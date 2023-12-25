package com.saha.genericdialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import com.saha.genericdialog.databinding.DialogLayoutBinding

class GenericDialog private constructor(val context: Context) {
    private lateinit var binding: DialogLayoutBinding

    private var dialog: Dialog? = null

    init {
        setupDialog()
    }

    // Initialize UI components
    private fun initializeViews() {
        binding.tvDialogTitle.visibility = View.GONE
        binding.ivDialogImage.visibility = View.GONE
        binding.tvDialogBodyText.visibility = View.GONE
        binding.btnNegative.visibility = View.GONE
        binding.btnPositive.visibility = View.GONE

    }


    fun showDialog() {
        val acc = context as Activity
        if (dialog != null && !acc.isFinishing && !acc.isDestroyed) {
            if (dialog!!.isShowing) {
                hideDialog()
            }
            dialog!!.show()
        }
    }

    fun hideDialog(){
        val acc = context as Activity
        if (dialog != null && !acc.isFinishing && !acc.isDestroyed) {
            dialog!!.dismiss()
        }
    }

    private fun setupDialog() {
        if (dialog == null)
            dialog = Dialog(context)
        binding = DialogLayoutBinding.inflate(LayoutInflater.from(context))
        dialog!!.setContentView(binding.root)

        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        initializeViews()
    }

    private fun setTitleText(title: String){
        binding.tvDialogTitle.text = title
        binding.tvDialogTitle.visibility = View.VISIBLE
    }

    private fun setBodyText(bodyText: String){
        binding.tvDialogBodyText.text = bodyText
        binding.tvDialogBodyText.visibility = View.VISIBLE
    }

    private fun setImageDrawable(image: Drawable){
        binding.ivDialogImage.setImageDrawable(image)
        binding.ivDialogImage.visibility = View.VISIBLE
    }

    private fun setImageActionListener(onImageClick: (() -> Unit)){
        if (binding.ivDialogImage.isVisible){
            binding.ivDialogImage.setOnClickListener{
                onImageClick.invoke()
            }
        }
    }

    private fun setPositiveButton(positiveButtonText: String, onPositiveButtonClick: (() -> Unit)?){
        binding.btnPositive.text = positiveButtonText
        binding.btnPositive.visibility = View.VISIBLE

        binding.btnPositive.setOnClickListener{
            onPositiveButtonClick?.invoke()
        }
    }

    private fun setPositiveButtonClickListener(onPositiveButtonClick: (() -> Unit)){
        binding.btnPositive.setOnClickListener{
            onPositiveButtonClick.invoke()
        }
    }

    private fun setNegativeButton(negativeButtonText: String, onNegativeButtonClick: (() -> Unit)?){
        binding.btnNegative.text = negativeButtonText;
        binding.btnNegative.visibility = View.VISIBLE;

        binding.btnNegative.setOnClickListener{
            onNegativeButtonClick?.invoke()
        }
    }
    private fun setNegativeButtonClickListener(onNegativeButtonClick: (() -> Unit)){
        binding.btnNegative.setOnClickListener{
            onNegativeButtonClick.invoke()
        }
    }

    private fun onDismissListener(onDismiss: (() -> Unit)){
        dialog?.setOnDismissListener{
            onDismiss.invoke()
        }
    }

    private fun setCancelable(cancelable: Boolean){
        dialog?.setCancelable(cancelable)
    }


    inner class Builder(context: Context) {
        private var onDialogDismiss: (() -> Unit)? = null
        private var onPositiveButtonClick: (() -> Unit)? = null
        private var onNegativeButtonClick: (() -> Unit)? = null
        private var imageAction: (() ->Unit)? = null

        private var titleText: String? = null
        private var bodyText: String? = null
        private var positiveButtonText: String? = null
        private var negativeButtonText: String? = null
        private var imageDrawable: Drawable? = null
        private var iconType: IconType? = null

        private var isTitleVisible = false
        private var isBodyTextVisible = false
        private var isPositiveButtonVisible = false
        private var isNegativeButtonVisible = false
        private var isImageVisible = false
        private var isDialogDismissListenerSet = false
        private var isDialogCancelable = true

        fun setTitleText(title: String) {
            this.titleText = title
            this.isTitleVisible = true
        }

        // Set body text
        fun setBodyText(body: String) {
            this.bodyText = body
            isBodyTextVisible = true
        }

        // Set image drawable
        fun setImageDrawable(imageDrawable: Drawable) {
            this.imageDrawable = imageDrawable
            isImageVisible = true
        }

        fun setImageAction(imageAction:()->Unit){
            this.imageAction = imageAction
        }

        fun setIconType(iconType: IconType){
            this.iconType = iconType
        }

        // Set positive button text and click listener
        fun setPositiveButton(title: String, clickListener: (() -> Unit)) {
            this.positiveButtonText = title
            isPositiveButtonVisible = true
            onPositiveButtonClick = clickListener
        }

        // Set negative button text and click listener
        fun setNegativeButton(title: String, clickListener: (() -> Unit)) {
            this.negativeButtonText = title
            isNegativeButtonVisible = true
            onNegativeButtonClick = clickListener
        }

        fun setDialogCancelListener(onDialogCancel: () -> Unit) {
            this.onDialogDismiss = onDialogCancel
            isDialogDismissListenerSet = true
        }

        fun setCancelable(cancelable: Boolean) {
            isDialogCancelable = cancelable
        }

        @SuppressLint("SuspiciousIndentation")
        fun build(): GenericDialog {
            val genericDialog = GenericDialog(context)

                genericDialog.setupDialog()

            if (isTitleVisible && titleText != null) {
                genericDialog.setTitleText(titleText!!)
            }

            if (isBodyTextVisible && bodyText != null){
                genericDialog.setBodyText(bodyText!!)
            }

            if (iconType != null){
                if(iconType == IconType.SUCCESS){
                    context.getDrawable(R.drawable.success)
                        ?.let { genericDialog.setImageDrawable(it) }
                }else if (iconType == IconType.ERROR){
                    context.getDrawable(R.drawable.error)?.let{
                        genericDialog.setImageDrawable(it)
                    }
                }else if (iconType == IconType.WARNING){
                    context.getDrawable(R.drawable.warning)?.let{
                        genericDialog.setImageDrawable(it)
                    }
                }
            }
            else if (isImageVisible && imageDrawable != null) {
                genericDialog.setImageDrawable(imageDrawable!!)
            }

            imageAction?.let {
                genericDialog.setImageActionListener(it)
            }

            if (isPositiveButtonVisible && positiveButtonText != null) {
                genericDialog.setPositiveButton(positiveButtonText!!, onPositiveButtonClick)
            }

            if (isNegativeButtonVisible && negativeButtonText != null) {
                genericDialog.setNegativeButton(negativeButtonText!!, onNegativeButtonClick)
            }

            onDialogDismiss?.let {
                genericDialog.onDismissListener(it)
            }

            genericDialog.setCancelable(isDialogCancelable)

            return genericDialog
        }
    }
    enum class IconType{
        ERROR,
        SUCCESS,
        WARNING
    }

    companion object {
        fun builder(activity: Activity): Builder {
            return GenericDialog(activity).Builder(activity)
        }

        fun getLoadingDialog(activity: Activity): LoadingDialog{
            return LoadingDialog(activity)
        }

    }
}