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
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MenuThanksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_todo_top, container, false)

        // ListViewの初期設定
        val lvTodoList: ListView = view.findViewById(R.id.lvTodoList)
        // acvtivityがnullの場合もあるため、letで動作させる
        val adapter = activity?.let {
            ArrayAdapter<Any>(
                it,
                android.R.layout.simple_list_item_1,
                mutableListOf()
            )
        }
        lvTodoList.adapter = adapter

        // 登録ボタンのクリックリスナーの設定
        val todoRegisterButton: Button = view.findViewById(R.id.btTodoRegisterButton)
        todoRegisterButton.setOnClickListener {
            // 入力テキストをひろう処理
            val editTodoInputText: EditText = view.findViewById(R.id.etTodoInput)
            val text: String = editTodoInputText.text.toString()
            // リストへの入力値の適用
            adapter?.add(text)
            // ここでDBの追加処理
            // DBの追加処理を書きたいが、コルーチンを使ってメインスレッドから切り離さないと行けない
            activity?.let {
                val database =
                    Room.databaseBuilder(
                        it.applicationContext,
                        TodoRoomDatabase::class.java,
                        "database-name"
                    )
                        .build()
                val todoDao = database.todoDao()
                val todo = Todo(0, text, "", false)

                // launch は昔の記法で、 0.26以降のバージョンではGlobalScope.launchの気泡となっている
                // TODO launchだけで十分かを調査する
                GlobalScope.launch {
                    todoDao.insert(todo)
                    Log.v("TAG", "after insert ${todoDao.getAll()}")
                }
            }
        }

        val btBackButton: Button = view.findViewById(R.id.btBackButton)
        btBackButton.setOnClickListener(ButtonClickListner())

        return view
    }

    private inner class ButtonClickListner : View.OnClickListener {
        override fun onClick(p0: View?) {
            activity?.finish()
        }
    }

    private fun menuListOf(): MutableList<MutableMap<String, String>> {
        val menuList: MutableList<MutableMap<String, String>> = mutableListOf()
        val menu = mutableMapOf("name" to "TODOアプリのサンプル", "detail" to "Webで拾った情報からTODOアプリを仕上げる")
        menuList.add(menu)
        return menuList
    }
}

