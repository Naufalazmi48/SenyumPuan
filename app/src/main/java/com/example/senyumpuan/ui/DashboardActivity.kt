package com.example.senyumpuan.ui

import android.os.Bundle
import com.example.senyumpuan.R
import com.example.senyumpuan.databinding.ActivityDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.role.observe(this, this::observerRole)

//        TODO CALL THIS PART IN ONCLICK ACTION WHEN WANT TO MOVE ACTIVITY
//        viewModel.route = DashboardRoutes.CHAT
//        viewModel.checkRoleUser()
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

    //    TODO DIRECTION TO CHAT OR MAP USER ACTIVITY
    private fun moveToActivityAsUser() {
        viewModel.route?.let {
            when (it) {
                DashboardRoutes.MAP -> {
//                    startActivity(Intent(this, SignInActivity::class.java))
                }
                DashboardRoutes.CHAT -> {
//                    startActivity(Intent(this, RegisterActivity::class.java))
                }
            }
        }
    }

    //    TODO DIRECTION TO CHAT OR MAP ADMIN ACTIVITY
    private fun moveToActivityAsAdmin() {
        viewModel.route?.let {
            when (it) {
                DashboardRoutes.MAP -> {
//                    startActivity(Intent(this, SignInActivity::class.java))
                }
                DashboardRoutes.CHAT -> {
//                    startActivity(Intent(this, RegisterActivity::class.java))
                }
            }
        }
    }
}