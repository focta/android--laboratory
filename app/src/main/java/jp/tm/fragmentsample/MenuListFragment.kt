package jp.tm.fragmentsample

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class MenuListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_menu_list, container, false)
        // 画面部品ListViewを取得
        val lvMenu: ListView = view.findViewById(R.id.lvMenu)

        // SimpleAdapterで使用するMutableListオブジェクトを用意。
        val menuList: MutableList<MutableMap<String, String>> = mutableListOf()
        // 「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        menuListOf(menuList)

        // SimpleAdapter第4引数from用データの用意。
        val from = arrayOf("name", "price")
        // SimpleAdapter第5引数to用データの用意。
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        // SimpleAdapterを生成。
        val adapter =
            SimpleAdapter(activity, menuList, android.R.layout.simple_list_item_2, from, to)
        // アダプタの登録。
        lvMenu.adapter = adapter

        // リスナの登録。
        lvMenu.onItemClickListener = ListItemClickListener()
        
        // インフレートされた画面を戻り値として返す。
        return view
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

            // タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMutableMap型!
            val item = parent.getItemAtPosition(position) as MutableMap<String, String>
            // 定食名と金額を取得。
            val menuName = item["name"]
            val menuDetail = item["detail"]

            // 引き継ぎデータをまとめて格納できるBundleオブジェクト生成。
            val bundle = Bundle()
            // Bundleオブジェクトに引き継ぎデータを格納。
            bundle.putString("menuName", menuName)
            bundle.putString("menuPrice", menuDetail)

            // enum で判断することに変更した
            val appType = AppType.getAppNameByPositionIndex(position)
            if (appType == AppType.TODO_APP) {
                val intent2MenuThanks = Intent(activity, MenuThanksActivity::class.java)
                intent2MenuThanks.putExtra("menuName", menuName)
                intent2MenuThanks.putExtra("menuPrice", menuDetail)
                startActivity(intent2MenuThanks)
            }
         }
    }

    private fun menuListOf(menuList: MutableList<MutableMap<String, String>>) {
        var menu = mutableMapOf("name" to "TODOアプリのサンプル", "detail" to "Webで拾った情報からTODOアプリを仕上げる")
        menuList.add(menu)
//        menu = mutableMapOf("name" to "ハンバーグ定食", "price" to "850円")
//        menuList.add(menu)
//        // 以下データ登録の繰り返し。
//        menu = mutableMapOf("name" to "生姜焼き定食", "price" to "850円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "ステーキ定食", "price" to "1000円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "野菜炒め定食", "price" to "750円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "とんかつ定食", "price" to "900円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "ミンチかつ定食", "price" to "850円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "チキンカツ定食", "price" to "900円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "コロッケ定食", "price" to "850円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "回鍋肉定食", "price" to "750円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "麻婆豆腐定食", "price" to "800円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "青椒肉絲定食", "price" to "900円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "焼き魚定食", "price" to "850円")
//        menuList.add(menu)
//        menu = mutableMapOf("name" to "焼肉定食", "price" to "950円")
//        menuList.add(menu)
    }

}
