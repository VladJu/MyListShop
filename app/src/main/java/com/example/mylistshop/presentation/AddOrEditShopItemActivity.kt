package com.example.mylistshop.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mylistshop.R
import com.example.mylistshop.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class AddOrEditShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: AddOrEditShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var tilCount: TextInputLayout
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode = MODE_SCREEN_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_edit_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[AddOrEditShopItemViewModel::class.java]
        initViews()
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    //подписываемся на объекты viewModel
    private fun observeViewModel() {
        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            //отображем ошибку
            tilCount.error = message
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }


    //устанавлваем правильный режим отображения
    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditScreen()
            MODE_ADD -> launchAddScreen()
        }
    }


    private fun addTextChangeListeners() {
        //при вводе текста надо скрывать ошибку
        etName.addTextChangedListener(object : TextWatcher {
            // до того как текст изменен
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            //в момент изменения текста
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
            //после того как текст был изменен
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    //запускает экран в режиме редактирвоания
    private fun launchEditScreen() {
        viewModel.getShopItemById(shopItemId)
        viewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }

    }

    private fun launchAddScreen() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_SCREEN_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, AddOrEditShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, AddOrEditShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

    //првоерям все параметры интнета не пустые ли они
    private fun parseIntent() {
        //если не содержит данного ключа
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        //если не содержит данного параметра
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        //еслм мы находимся в режиме редактирования, то в интенте должен присутствовать параметр
        //EXTRA_SHOP_ITEM_ID
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            //если содрежит данный ключь
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }

    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        etName = findViewById(R.id.et_name)
        tilCount = findViewById(R.id.til_count)
        etCount = findViewById(R.id.et_count)
        buttonSave = findViewById(R.id.button_save)
    }
}