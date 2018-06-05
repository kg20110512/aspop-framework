/**
 * 
 */
package com.vyd.base.utils.captcha;

import java.awt.Color;

import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.service.Captcha;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

/**
 * <p>Description: 验证码工具类，基于patchca</p>
 * @author Dirk
 * @date 2017年5月19日 上午10:47:17 
 */
public class CaptchaUtil {
	private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
	
	/**
	 * 验证码默认长度
	 */
	private static final int CAPTCHA_DEFAULT_LENGTH = 4;
	
	/**
	 * 数字字符串集合
	 */
	private static final String NUMBER_CHAR = "0123456789";
	
	/**
	 * <p>Description: 获取指定长度范围的字母及数字验证码</p>
	 * @param minLength 最小长度
	 * @param maxLength 最大长度
	 * @return 生成的验证码
	 */
	public static Captcha getCaptcha(int minLength, int maxLength) {
		RandomWordFactory wordFactory = new RandomWordFactory();
		wordFactory.setMaxLength(maxLength);
		wordFactory.setMinLength(minLength);
		cs.setWordFactory(wordFactory);
		cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
		cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
		return cs.getCaptcha();
	}
	
	/**
	 * <p>Description: 获取指定长度的字母及数字验证码</p>
	 * @param length 验证码长度
	 * @return 生成的验证码
	 */
	public static Captcha getCaptcha(int length) {
		return getCaptcha(length,length);
	}
	
	/**
	 * <p>Description: 获取字母及数字验证码，默认长度为4</p>
	 * @return 生成的验证码
	 */
	public static Captcha getCaptcha() {
		return getCaptcha(CAPTCHA_DEFAULT_LENGTH);
	}
	
	/**
	 * <p>Description: 获取指定长度范围的数字验证码</p>
	 * @param minLength 最小长度
	 * @param maxLength 最大长度
	 * @return 生成的验证码
	 */
	public static Captcha getNumberCaptcha(int minLength, int maxLength) {
		RandomWordFactory wordFactory = new RandomWordFactory();
		wordFactory.setCharacters(NUMBER_CHAR);
		wordFactory.setMinLength(minLength);
		wordFactory.setMaxLength(maxLength);
		cs.setWordFactory(wordFactory);
		cs.setColorFactory(new SingleColorFactory(new Color(20, 60, 170)));
		cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
		return cs.getCaptcha();
	}
	
	/**
	 * <p>Description: 获取指定长度的数字验证码</p>
	 * @param length 指定长度
	 * @return 生成的验证码
	 */
	public static Captcha getNumberCaptcha(int length) {
		return getNumberCaptcha(length, length);
	}
	
	/**
	 * <p>Description: 获取数字验证码，默认长度为4</p>
	 * @return 生成的验证码
	 */
	public static Captcha getNumberCaptcha() {
		return getNumberCaptcha(CAPTCHA_DEFAULT_LENGTH);
	}
}