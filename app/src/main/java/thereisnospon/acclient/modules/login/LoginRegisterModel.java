package thereisnospon.acclient.modules.login;

import android.content.Context;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Response;
import thereisnospon.acclient.R;
import thereisnospon.acclient.api.HdojApi;
import thereisnospon.acclient.utils.net.HttpUtil;
import thereisnospon.acclient.utils.net.request.IRequest;

/**
 * @author thereisnospon
 * 登陆注册具体实现
 * Created by yzr on 16/10/30.
 */

final class LoginRegisterModel implements LoginRegisterContact.Model {


	@Override
	public String login(String name, String password) {
		return parseName(name, loginHtml(name, password));
	}

	private boolean checkId(String id, Element a) {
		String href = a.attr("href");
		if (href == null) {
			return false;
		}
		String ids[] = href.split("user=");
		return ids.length == 2 && id.equals(ids[1]);
	}


	private String parseName(String id, String html) {
		Document document = Jsoup.parse(html);
		Elements as = document.getElementsByTag("a");
		int len = as != null ?
		          as.size() :
		          0;
		for (int i = 0;
				i < len;
				i++) {
			Element a = as.get(i);
			Elements c = a.getElementsByAttributeValue("alt", "Author");
			if (c != null && c.size() > 0 && checkId(id, a)) {
				return a.text();
			}
		}
		return null;
	}

	private String loginHtml(String userName, String password) {
		try {
			Response res = HttpUtil.getInstance()
			                       .post(HdojApi.LOGIN)
			                       .addParameter("username", userName)
			                       .addParameter("userpass", password)
			                       .addParameter("login", "Sign In")
			                       .execute();
			return new String(res.body()
			                     .bytes(), "gb2312");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}


	@Override
	public String register(String name, String email, String password, String checkPassword, String check) {
		return reg(name, email, password, checkPassword, check);
	}


	private String reg(String name, String email, String password, String checkPassword, String check) {
		return inner_register(name, email, password, checkPassword, check) ?
		       name :
		       null;
	}


	private boolean inner_register(String name, String email, String password, String checkPassword, String check) {
		try {
			IRequest request = HttpUtil.getInstance()
			                           .post(HdojApi.REGISTER_URL)
			                           .addParameter("username", name)
			                           .addParameter("email", email)
			                           .addParameter("password1", password)
			                           .addParameter("password2", checkPassword);
			if (check != null) {
				request.addParameter("check", check);
			}
			Response respone = request.execute();
			if (!respone.isSuccessful()) {
				return false;
			} else {
				String html = new String(respone.body()
				                                .bytes(), "gb2312");
				return checkRegister(html);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	private static boolean checkRegister(String html) {
		if (html == null) {
			return false;
		}
		Document document = Jsoup.parse(html);
		Elements inputs = document.getElementsByTag("input");
		for (int i = 0;
				inputs != null && i < inputs.size();
				i++) {
			String name = inputs.get(i)
			                    .attr("name");
			if ("idnum".equals(name)) {
				return true;
			}
		}
		return false;
	}




	@Override
	public void checkCode(Context cxt, ImageView imageView) {
		AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(cxt, R.drawable.ic_animated_loading_pb);
		imageView.setImageDrawable(animatedVectorDrawableCompat);
		AnimatedVectorDrawableCompat dr = (AnimatedVectorDrawableCompat) imageView.getDrawable();
		dr.start();

		List<Cookie>cookies=HttpUtil.getInstance()
				.getCookiesManager()
				.loadForRequest(HttpUrl.parse("http://bestcoder.hdu.edu.cn/ck.php"));

		final StringBuilder sb=new StringBuilder();

		for(Cookie cookie:cookies){
			sb.append(cookie.name()+"="+cookie.value());
		}

		final Map<String,String>map=new HashMap<>();
		map.put("Cookie",sb.toString());


		//带cookie 的图片请求
		GlideUrl url=new GlideUrl(HdojApi.CHECK_CODE, new Headers() {
			@Override
			public Map<String, String> getHeaders() {
				return map;
			}
		});

		Glide.with(cxt)
		     .load(url)
		     .asBitmap()
		     .dontAnimate()
		     .skipMemoryCache(true)
		     .diskCacheStrategy(DiskCacheStrategy.ALL)

		     .into(imageView);
	}
}
