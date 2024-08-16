package com.example.thirdsemproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thirdsemproject.R
import com.example.thirdsemproject.adapter.user.CartAdapter
import com.example.thirdsemproject.adapter.user.ProductUserAdapter
import com.example.thirdsemproject.databinding.FragmentCartBinding
import com.example.thirdsemproject.repository.cart.CartRepoImpl
import com.example.thirdsemproject.viewmodel.CartViewModel


class CartFragment : Fragment() {

    lateinit var cartBinding : FragmentCartBinding
    lateinit var cartViewModel: CartViewModel
    lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartBinding = FragmentCartBinding.inflate(inflater,container,false)
        return cartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var repo = CartRepoImpl()
        cartViewModel = CartViewModel(repo)
        cartViewModel.getCartByUserID()

        cartAdapter = CartAdapter(
            requireContext(),
            ArrayList(),
            cartViewModel
        )

        cartViewModel.cartData.observe(requireActivity()){cart->
           if(cart.isNullOrEmpty()){
               cartBinding.cartCheck.visibility =View.VISIBLE
               cartBinding.recyclerCart.visibility =View.GONE
           }else{
               cartBinding.cartCheck.visibility =View.GONE
               cartBinding.recyclerCart.visibility =View.VISIBLE
               cartAdapter.updateData(cart)

           }
        }

        cartViewModel.loadingState.observe(requireActivity()){loading->
            if(loading){
                cartBinding.progressBarCartDashboard.visibility= View.VISIBLE

            }else{
                cartBinding.progressBarCartDashboard.visibility= View.GONE

            }
        }

        cartBinding.recyclerCart.apply{

            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }

    }
}