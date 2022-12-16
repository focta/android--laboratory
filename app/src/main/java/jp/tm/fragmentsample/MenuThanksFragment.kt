package jp.tm.fragmentsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class MenuThanksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_menu_thanks, container, false)

        val intent = activity?.intent
        val extras = intent?.extras

        val menuName = extras?.getString("menuName")
        val menuPrice = extras?.getString("menuPrice")

        val tvMenuName: TextView = view.findViewById(R.id.tvMenuName)
        val tvMenuPrice: TextView = view.findViewById(R.id.tvMenuPrice)
        tvMenuName.text = menuName
        tvMenuPrice.text = menuPrice

        val btBackButton: Button = view.findViewById(R.id.btBackButton)
        btBackButton.setOnClickListener(ButtonClickListner())

        return view
    }


    private inner class ButtonClickListner : View.OnClickListener {
        override fun onClick(p0: View?) {
            activity?.finish()
        }
    }

}
