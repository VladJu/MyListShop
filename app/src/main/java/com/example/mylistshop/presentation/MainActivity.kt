package com.example.mylistshop.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mylistshop.R
import com.example.mylistshop.databinding.ActivityMainBinding
import com.example.mylistshop.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShop: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as AppShopList).component
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapterShop.submitList(it)
        }
        binding.buttonAddShopItem.setOnClickListener {
            if (bookOrientationThePage()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
        thread {
            //contentResolver -необходим  для того чтобы отправлять любые запросы в ContentProvider
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.mylistshop/shop_items"),
                null,
                null,
                null,
                null,
                null
            )
            //получаем из Cursor данные
            //при 1 вызове он переместиться к записи с id =0 и вернет true, провалимся в цикл прочитаем данные
            //и снова вызовем moveToNext()
            //Сразу из Cursor не можем получить все данные, нам надо считывать данные из конкретных таблиц
            while (cursor?.moveToNext() == true) {
                //получаем значения колонок по их индексу
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0
                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                Log.d("MainActivity", shopItem.toString())
            }
            cursor?.close()
        }
    }


    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


    private fun bookOrientationThePage(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        with(binding.rvShopList) {
            adapterShop = ShopListAdapter()
            adapter = adapterShop
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupChangeEnableStateLongClickListener()
        setupDetailInfoShopItemCLickListener()
        setupDeleteItemSwipeClickListener(binding.rvShopList)
    }


    private fun setupChangeEnableStateLongClickListener() {
        adapterShop.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupDetailInfoShopItemCLickListener() {
        adapterShop.onShopItemClickListener = {
            if (bookOrientationThePage()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun setupDeleteItemSwipeClickListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterShop.currentList[viewHolder.adapterPosition]
                //viewModel.deleteShopItem(item)
                thread {
                    //4)
                    contentResolver.delete(
                        Uri.parse("content://com.example.mylistshop/shop_items"),
                        null,
                        arrayOf(item.id.toString())
                    )
                }
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
}