package com.test.testtdlib.presentation.main.chat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.testtdlib.databinding.FragmentChatListBinding
import com.test.testtdlib.di.Injector
import com.test.testtdlib.presentation.base.BaseFragment
import com.test.testtdlib.presentation.extensions.hideKeyboard
import com.test.testtdlib.presentation.main.chat.adapter.ContactsAdapter
import org.drinkless.td.libcore.telegram.TdApi

class ChatListFragment : BaseFragment<ChatListViewModel>() {

    companion object {
        private const val PERMISSION_READ_CONTACTS_REQUEST_CODE = 128
    }

    override fun inject() = Injector.mainComponent().inject(this)

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    private lateinit var binding: FragmentChatListBinding

    private lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ContactsAdapter(actionContactListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.chatListView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatListView.adapter = adapter
        binding.chatListView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.swap(contacts)
        }

        binding.btnRequestPermissionReadContacts.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                checkPermissionReadContacts()
            } else {
                binding.btnRequestPermissionReadContacts.visibility = View.GONE
                binding.labelNeedPermission.text = "Go app settings and allowed read contacts"
            }
        }
        checkPermissionReadContacts()

        hideKeyboard()
    }

    private fun checkPermissionReadContacts() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSION_READ_CONTACTS_REQUEST_CODE
            )
        } else {
            binding.frameRequestPermission.visibility = View.GONE
            viewModel.getContacts()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_READ_CONTACTS_REQUEST_CODE) {
            if (permissions[0] == Manifest.permission.READ_CONTACTS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binding.frameRequestPermission.visibility = View.GONE
                viewModel.importContacts()
            } else {
                binding.frameRequestPermission.visibility = View.VISIBLE
            }
        }
    }

    private val actionContactListener = object : ContactsAdapter.ActionListener {
        override fun onClick(item: TdApi.User) {
            val chatID = item.id
            viewModel.openChat(chatID)
        }

    }
}