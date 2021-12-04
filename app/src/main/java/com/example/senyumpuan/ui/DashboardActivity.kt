package com.example.senyumpuan.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityDashboardBinding
import com.example.senyumpuan.ui.desa_binaan.DesaBinaanActivity
import com.example.senyumpuan.ui.ruang_aman.RuangAmanActivity
import com.example.senyumpuan.ui.sign_in.SignInActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardActivity : BaseActivity<ActivityDashboardBinding>(), View.OnClickListener {

    private val viewModel: DashboardViewModel by viewModel()

    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.dashboard)

        setupListener()
        viewModel.role.observe(this, this::observerRole)
    }

    private fun setupListener() {
        with(binding) {
            more.setOnClickListener(this@DashboardActivity)
            menu1.setOnClickListener(this@DashboardActivity)
            menu2.setOnClickListener(this@DashboardActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sign_out_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out){
            showAlertSignOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertSignOut() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.sign_out))
            .setMessage(getString(R.string.alert_message))
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton(android.R.string.ok
            ) { _, _ ->
                if (viewModel.signOut()){
                    startActivity(
                        Intent(this, SignInActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                }
            }
            .setNegativeButton(android.R.string.cancel, null).show()
    }

    private fun observerRole(role: String?) {
        role?.let {
            if (it.contains(getString(R.string.role_admin))) {
                moveToActivityAsAdmin()
            } else if (it.contains(getString(R.string.role_user), true)) {
                moveToActivityAsUser()
            }
        }
    }

    private fun moveToActivityAsUser() {
        viewModel.route?.let {
            when (it) {
                DashboardRoutes.MAP -> {
                    startActivity(Intent(this, DesaBinaanActivity::class.java))
                }
                DashboardRoutes.CHAT -> {
                    startActivity(
                        Intent(this, RuangAmanActivity::class.java)
                    )
                }
            }
        }
    }

    private fun moveToActivityAsAdmin() {
        viewModel.route?.let {
            when (it) {
                DashboardRoutes.MAP -> {
                    startActivity(Intent(this, DesaBinaanActivity::class.java))
                }
                DashboardRoutes.CHAT -> {
                    startActivity(
                        Intent(
                            this,
                            Class.forName("com.example.admin.ui.ruang_aman.ListChatActivity")
                        )
                    )
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.more -> {
                val uri = Uri.parse(getString(R.string.instagram))
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }

            R.id.menu1 -> {
                viewModel.route = DashboardRoutes.CHAT
                viewModel.checkRoleUser()
            }

            R.id.menu2 -> {
                viewModel.route = DashboardRoutes.MAP
                viewModel.checkRoleUser()
            }
        }
    }
}