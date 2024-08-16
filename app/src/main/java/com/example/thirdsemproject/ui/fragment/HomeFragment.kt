package com.example.thirdsemproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thirdsemproject.R
import com.example.thirdsemproject.adapter.user.CategoryUserAdapter
import com.example.thirdsemproject.adapter.user.ProductUserAdapter
import com.example.thirdsemproject.databinding.FragmentHomeBinding
import com.example.thirdsemproject.repository.category.CategoryRepoImpl
import com.example.thirdsemproject.repository.product.ProductRepoImpl
import com.example.thirdsemproject.viewmodel.CategoryViewModel
import com.example.thirdsemproject.viewmodel.ProductViewModel

class HomeFragment : Fragment(){

    lateinit var homeBinding: FragmentHomeBinding
    lateinit var categoryViewModel:CategoryViewModel
    lateinit var productViewModel: ProductViewModel
    lateinit var  categoryUserAdapter:CategoryUserAdapter
    lateinit var productUserAdapter:ProductUserAdapter





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var categoryRepo = CategoryRepoImpl()
        categoryViewModel = CategoryViewModel(categoryRepo)
        categoryViewModel.getAllCategory()
        var productRepo = ProductRepoImpl()
        productViewModel = ProductViewModel(productRepo)
        productViewModel.getAllProduct()

        productUserAdapter = ProductUserAdapter(
            requireContext(),
            ArrayList()
        )
        categoryUserAdapter = CategoryUserAdapter(
            requireContext(),
            ArrayList()
        )

        productViewModel.productData.observe(requireActivity()){product->
            product?.let{
                productUserAdapter.updateData(it)
            }
        }
        categoryViewModel.categoryData.observe(requireActivity()){category->
            category?.let{
                categoryUserAdapter.updateData(it)
            }
        }


        categoryViewModel.loadingState.observe(requireActivity()){loading->
            if(loading){
                homeBinding.progressBarUserCategory.visibility= View.VISIBLE

            }else{
                homeBinding.progressBarUserCategory.visibility= View.GONE

            }
        }


        productViewModel.loadingState.observe(requireActivity()){loading->
            if(loading){
                homeBinding.progressBarUserProduct.visibility= View.VISIBLE

            }else{
                homeBinding.progressBarUserProduct.visibility= View.GONE

            }
        }

        homeBinding.recyclerViewCategoryUser.apply{
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = categoryUserAdapter
        }

        homeBinding.recyclerViewProductUser.apply{
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = productUserAdapter
        }





    }
}
