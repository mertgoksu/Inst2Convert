package com.mertg.inst2convert.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<T : ViewBinding, VM : BaseViewModel>(
    @LayoutRes private val contentLayoutId: Int
) : Fragment() {

    protected lateinit var viewModel: VM
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(getViewModelClass())

        initUI()
        observeBaseViewModel()
    }

    abstract fun initUI()

    abstract fun getViewModelClass(): Class<VM>

    private fun observeBaseViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            handleLoading(isLoading)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            handleError(errorMessage)
        }
    }

    open fun handleLoading(isLoading: Boolean) {
        // Yükleme durumunu override edilebilir
    }

    open fun handleError(errorMessage: String) {
        // Hata durumunu override edilebilir
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
