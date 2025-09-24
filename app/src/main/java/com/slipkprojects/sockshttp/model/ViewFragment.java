package com.slipkprojects.sockshttp.model;

import com.slipkprojects.sockshttp.SocksHttpMainActivity;
import androidx.fragment.app.Fragment;
import android.view.View;

public abstract class ViewFragment extends Fragment
	implements OnUpdateLayout
{
	public void updateLayout()
	{
		updateLayout(null);
	}
}