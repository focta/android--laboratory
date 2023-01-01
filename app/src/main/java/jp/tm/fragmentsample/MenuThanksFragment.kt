package jp.tm.fragmentsample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MenuThanksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_todo_top, container, false)

        // ListViewの初期設定
        val lvTodoList: ListView = view.findViewById(R.id.lvTodoList)

        // TODO 初期表示時にDBに登録されているデータを取得したい！
        val database = TodoRoomDatabase.getDatabase(requireContext())
        val todoDao = database.todoDao()

        // https://stackoverflow.com/questions/66865525/withcontextdispatchers-io-how-to-use-for-room
//        GlobalScope.launch {
//            getTodoStart(todoDao, lvTodoList)
//        }
        // TODO lifecycleScopeの実装をしてみたが、動作のエラーが発生している
        viewLifecycleOwner.lifecycleScope.launch {
            getTodoStart(todoDao, lvTodoList)
        }
        Log.v("TAG", "取得のメソッドを抜けた")


        // 登録ボタンのクリックリスナーの設定
        val todoRegisterButton: Button = view.findViewById(R.id.btTodoRegisterButton)
        todoRegisterButton.setOnClickListener {
            // 入力テキストをひろう処理
            val editTodoInputText: EditText = view.findViewById(R.id.etTodoInput)
            val text: String = editTodoInputText.text.toString()
            // ここの順番をDB登録と入れ替えて、入力したデータを永続化した後に表示を切り替える構成にする
            // ここでDBの追加処理
            val todo = Todo(0, text, "", false)

            // launch は昔の記法で、 0.26以降のバージョンではGlobalScope.launchの記法となっている
            // DBの追加処理を書きたいが、コルーチンを使ってメインスレッドから切り離さないと行けない
            // TODO launchだけで十分かを調査する
            GlobalScope.launch {
                todoDao.insert(todo)
                Log.v("TAG", "after insert ${todoDao.getAll()}")
            }
            // リストへの入力値の適用
//            adapter?.add(text)
        }

        val btBackButton: Button = view.findViewById(R.id.btBackButton)
        btBackButton.setOnClickListener(ButtonClickListner())

        return view
    }

    // UIスレッドで更新しないためにメソッド切り出しを行うと良いらしい
    // https://qiita.com/shin1007/items/414496c5910a3443ed5a
    private suspend fun getTodoStart(todoDao: TodoDao, lvTodoList: ListView) =
        withContext(Dispatchers.IO) {

            val tmpAllTodo = async { todoDao.getAll() }
            val await = tmpAllTodo.await()
            Log.v("TAG", "取得結果: $await")
            // TODO asyncで実施した結果を受け取って表示処理を作りたいのだが、単純にawait()で書くことはできなさそう・・・

            // activityがnullの場合もあるため、letで動作させる
            val adapter = activity?.let {
                ArrayAdapter<Any>(
                    it, android.R.layout.simple_list_item_1,
                    //                allTodo as List<Any>
                    mutableListOf()
                )
            }
            lvTodoList.adapter = adapter
        }


    private inner class ButtonClickListner : View.OnClickListener {
        override fun onClick(p0: View?) {
            activity?.finish()
        }
    }

}

