package io.jpress.core;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.jfinal.core.Controller;
import com.jfinal.i18n.Res;

public class JBaseController extends Controller {
	
	private JSession session;
	public JBaseController() {
		session = new JSession(this);
	}
	
	@Override
	public String getPara(String name) {
		String result = getRequest().getParameter(name);
		if(null != result ){
			return Jsoup.clean(result, Whitelist.relaxed());
		}
		return null;
	}
	
	@Override
	public String getPara(String name,String defaultValue) {
		String result = getRequest().getParameter(name);
		if(null != result ){
			return Jsoup.clean(result, Whitelist.relaxed());
		}
		return defaultValue;
	}

	public boolean isAjax() {
		String header = getRequest().getHeader("X-Requested-With");
		return "XMLHttpRequest".equalsIgnoreCase(header);
	}
	
	public boolean isMultipartRequest(){
		String contentType = getRequest().getContentType();
		return contentType != null && contentType.toLowerCase().indexOf("multipart") != -1 ;
	}


	protected int getPageNumbere() {
		int page = getParaToInt("page", 1);
		if (page < 1) {
			page = 1;
		}
		return page;
	}

	protected int getPageSize() {
		int size = getParaToInt("size", 10);
		if (size < 1) {
			size = 1;
		}
		return size;
	}
	
	public void setHeader(String key,String value){
		getResponse().setHeader(key, value);
	}
	
	
	public Res getI18nRes(){
		//Attribute set in JI18nInterceptor.class
		return getAttr("i18n");
	}
	
	public String getI18nValue(String key){
		return getI18nRes().get(key);
	}
	
	
	public void renderAjaxResultForSuccess(){
		renderAjaxResult("success",0,null);
	}
	
	public void renderAjaxResultForSuccess(String message){
		renderAjaxResult(message,0,null);
	}
	
	public void renderAjaxResultForError(){
		renderAjaxResult("error", 1, null);
	}
	
	public void renderAjaxResultForError(String message){
		renderAjaxResult(message, 1, null);
	}
	
	
	public void renderAjaxResult(String message,int errorCode){
		renderAjaxResult(message, errorCode, null);
	}
	
	public void renderAjaxResult(String message,int errorCode,Object data){
		AjaxResult ar = new AjaxResult();
		ar.setMessage(message);
		ar.setErrorCode(errorCode);
		ar.setData(data);
		renderJson(ar);
	}
	
	
	@Override
	public void createToken() {
		createToken("jtoken");
	}
	
	@Override
	public boolean validateToken() {
		return validateToken("jtoken");
	}

	@Override
	public HttpSession getSession() {
		return session;
	}

	@Override
	public HttpSession getSession(boolean create) {
		return getSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSessionAttr(String key) {
		return (T) session.getAttribute(key);
	}

	@Override
	public Controller setSessionAttr(String key, Object value) {
		session.setAttribute(key, value);
		return this;
	}
	
	@Override
	public Controller removeSessionAttr(String key) {
		session.removeAttribute(key);
		return this;
	}

	@Override
	public void renderCaptcha() {
		render(new JCaptchaRender(this));
	}
	
	@Override
	public boolean validateCaptcha(String paraName) {
		return JCaptchaRender.validate(this, getPara(paraName));
	}
	
}
