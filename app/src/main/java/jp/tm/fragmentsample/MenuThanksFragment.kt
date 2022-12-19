package jp.tm.fragmentsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment

class MenuThanksFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_todo_top, container, false)

//        val intent = activity?.intent
//        val extras = intent?.extras

//        val menuName = extras?.getString("menuName")
//        val menuPrice = extras?.getString("menuPrice")

//        val tvMenuName: TextView = view.findViewById(R.id.tvMenuName)
//        val tvMenuPrice: TextView = view.findViewById(R.id.tvMenuPrice)
//        tvMenuName.text = menuName
//        tvMenuPrice.text = menuPrice

        // 登録ボタンのクリックリスナーの設定
        val todoRegisterButton: Button = view.findViewById(R.id.btTodoRegisterButton)
        todoRegisterButton.setOnClickListener{
            // 入力テキストをひろう処理
            val editTodoInputText: EditText = view.findViewById(R.id.etTodoInput)
            val text: String = editTodoInputText.text.toString()
            println("入力したテキスト: $text")
            // TODO リストへの入力値の適用


            val lvTodoList: ListView = view.findViewById(R.id.lvTodoList)
            val menuList = menuListOf()
            // SimpleAdapter第4引数from用データの用意。
            val from = arrayOf("name", "detail")
            // SimpleAdapter第5引数to用データの用意。
            val to = intArrayOf(android.R.id.text1, android.R.id.text2)
            // SimpleAdapterを生成。
            val adapter =
                SimpleAdapter(activity, menuList, android.R.layout.simple_list_item_2, from, to)
            lvTodoList.adapter = adapter

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
