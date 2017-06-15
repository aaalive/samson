// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package info.samson.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;


import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import info.samson.R;
import info.samson.adaptres.GridIconAdapter;
import java.util.Iterator;
import java.util.List;

public class SocialFrag extends Fragment
{

	final String mUrls[] 	= {	"", "", "", "https://www.youtube.com/user/israelrent", "https://plus.google.com/u/0/+AnastasiyaLiverant/posts", "https://twitter.com/israelrent", "https://www.facebook.com/groups/israelrent/", "http://www.odnoklassniki.ru/group/47903201034321", "http://vk.com/club24499543", "http://nasyasanya.livejournal.com/"
	};
	public SocialFrag()
	{
	}

	protected void call(String s)
	{
		startActivity(new Intent("android.intent.action.CALL", Uri.parse((new StringBuilder("tel:")).append(s).toString())));
	}

	public void goToMarket(Context context)
	{
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.skype.raider"));
		//intent.setFlags(0x10000000);

		context.startActivity(intent);
	}

	public void initiateSkypeUri(Context context, String s)
	{
		if (!isSkypeClientInstalled(context))
		{
			goToMarket(context);
			return;
		} else
		{
			Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(s));
			intent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
			//intent.setFlags(0x10000000);
			context.startActivity(intent);
			return;
		}
	}

	public boolean isSkypeClientInstalled(Context context)
	{
		PackageManager packagemanager = context.getPackageManager();
		try
		{
			packagemanager.getPackageInfo("com.skype.raider", 1);
		}
		catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
		{
			return false;
		}
		return true;
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);


	}

	public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
	{
		if (viewgroup == null)
		{
			return null;
		} else
		{
			return layoutinflater.inflate(R.layout.social, viewgroup, false);
		}


	}

	public void onStart()
	{
		super.onStart();

		GridView gridview = (GridView)getView().findViewById(R.id.icon_grid);
		LinearLayout cont = (LinearLayout)getView().findViewById(R.id.social_cont);
		gridview.setAdapter(new GridIconAdapter(getActivity()));
		gridview.setOnItemClickListener(new OnItemClickListener() {
			//
			//			final SocialFrag this$0;
			//			private final String val$mUrls[];

			public void onItemClick(AdapterView adapterview, View view, int i, long l)
			{

				switch(i){
				case 0:
					if (((TelephonyManager)getActivity().getSystemService("phone")).getPhoneType() == 0)
					{
						Toast.makeText(getActivity(), "\u0422\u0435\u043B\u0435\u0444\u043E\u043D\u043D\u044B\u0435 \u0437\u0432\u043E\u043D\u043A\u0438 \u043D\u0435 \u043F\u043E\u0434\u0434\u0435\u0440\u0436\u0438\u0432\u0430\u044E\u0442\u0441\u044F", 1);
						Intent intent1 = new Intent("android.intent.action.VIEW");
						intent1.setData(Uri.parse("http://israelrent.info/index/contacts/0-12"));
						startActivity(intent1);

					} else
					{
						call("+972547549913");
					}
					break;
				case 1:
					openWhatsappContact("972547549913");
					break;
				case 2:
					initiateSkypeUri(getActivity(), "skype:livernet13?chat");
					break;


				default:
					Intent intent = new Intent("android.intent.action.VIEW");
					intent.setData(Uri.parse(mUrls[i]));
					startActivity(intent);
					break;

				}

			}





		});
	}

	void openWhatsappContact(String s)
	{
		Intent sendIntent = new Intent(Intent.ACTION_SEND);     
		sendIntent.setType("text/plain");
		sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

		PackageManager pm = getActivity().getApplicationContext().getPackageManager();
		final List<ResolveInfo> matches = pm.queryIntentActivities(sendIntent, 0);
		boolean temWhatsApp = false;
		for (final ResolveInfo info : matches) {
			if (info.activityInfo.packageName.startsWith("com.whatsapp"))  {
				final ComponentName name = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
				sendIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
				sendIntent.setComponent(name);
				temWhatsApp = true;
				break;
			}
		}      
		if(temWhatsApp){
			Intent intent1 = new Intent("android.intent.action.SENDTO", Uri.parse((new StringBuilder("smsto:")).append(s).toString()));
			intent1.setPackage("com.whatsapp");
			startActivity(Intent.createChooser(intent1, ""));
		}
		else{
			Toast.makeText(getActivity(), "\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u0443\u0441\u0442\u0430\u043D\u043E\u0432\u0438\u0442\u0435 whatsapp", 1);
			Intent intent2 = new Intent("android.intent.action.VIEW");
			intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
			startActivity(intent2);
		}
		//		ResolveInfo iterator = getActivity().getApplicationContext().getPackageManager().queryIntentActivities(intent, 0).iterator();
		//
		//		if (!((ResolveInfo)iterator.next()).activityInfo.packageName.startsWith("com.whatsapp")) {
		//					boolean flag1;
		//					boolean flag = iterator.hasNext();
		//					flag1 = false;
		//					if (flag)
		//					{
		//						continue; /* Loop/switch isn't completed */
		//					}
		//					else {
		//						Iterator iterator;
		//						Intent intent = new Intent("android.intent.action.SEND");
		//						intent.setType("text/plain");
		//						iterator = getActivity().getApplicationContext().getPackageManager().queryIntentActivities(intent, 0).iterator();
		//					}
		//					flag1 = true;
		//		
		//					if (flag1)
		//					{
		//						Intent intent1 = new Intent("android.intent.action.SENDTO", Uri.parse((new StringBuilder("smsto:")).append(s).toString()));
		//						intent1.setPackage("com.whatsapp");
		//						startActivity(Intent.createChooser(intent1, ""));
		//						return;
		//					} else
		//					{
		//						Toast.makeText(getActivity(), "\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u0443\u0441\u0442\u0430\u043D\u043E\u0432\u0438\u0442\u0435 whatsapp", 1);
		//						Intent intent2 = new Intent("android.intent.action.VIEW");
		//						intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
		//						startActivity(intent2);
		//						return;
		//					}
		//					}
	}

	
}