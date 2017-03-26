package thereisnospon.acclient.modules.login;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class ErrorConstants {

	static final int NO_EMPTY_USERNAME = 0x0;
	static final int WRONG_EMAIL = 0x1;
	static final int NO_EMPTY_PASSWORD = 0x2;
	static final int PASSWORD_NOT_EQUAL = 0x3;
	static final int PASSWORD_SHORT = 0x4;
	static final int REGISTER_UNSUCCESSFULLY = 0x5;


	@IntDef(value = { NO_EMPTY_USERNAME,
	                  WRONG_EMAIL,
	                  NO_EMPTY_PASSWORD,
	                  PASSWORD_NOT_EQUAL,
	                  PASSWORD_SHORT,
	                  REGISTER_UNSUCCESSFULLY})

	@Retention(RetentionPolicy.SOURCE)
	@interface Value {}
}