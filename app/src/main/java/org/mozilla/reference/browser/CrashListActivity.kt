/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.reference.browser

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import mozilla.components.lib.crash.CrashReporter
import mozilla.components.lib.crash.ui.AbstractCrashListFragment
import mozilla.components.support.ktx.android.view.setupPersistentInsets
import org.mozilla.reference.browser.ext.requireComponents

/**
 * A simple activity whose only purpose is to load the [CrashListFragment].
 */
class CrashListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
        window.setupPersistentInsets()

        supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, CrashListFragment())
            .commit()
    }
}

/**
 * An [AbstractCrashListFragment] implementor that uses the application [CrashReporter].
 */
class CrashListFragment : AbstractCrashListFragment() {
    override val reporter: CrashReporter by lazy { requireComponents.analytics.crashReporter }

    override fun onCrashServiceSelected(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = url.toUri()
            `package` = context?.packageName
        }
        startActivity(intent)
        activity?.finish()
    }
}
