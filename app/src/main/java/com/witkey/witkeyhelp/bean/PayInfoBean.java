package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

public class PayInfoBean {


	/**
	 * errorCode : 200
	 * errorMessage : 请求成功
	 * returnObject : {"package":"Sign=WXPay","out_trade_no":"201912301426284609k43z779E7gWZn9","appid":"wx77ff4c8528dac183","sign":"EC04B1611CA2339D31CE6D8BF2321515","package1":"Sign=WXPay","partnerid":"1567167441","prepayid":"wx301426287091205ba30e6c981036260500","noncestr":"7eRrMA6b9yGRLe4giy0ftZzmfMg1gPZy","timestamp":"1577687188"}
	 */

	private String errorCode;
	private String errorMessage;
	private ReturnObjectBean returnObject;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ReturnObjectBean getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(ReturnObjectBean returnObject) {
		this.returnObject = returnObject;
	}

	public static class ReturnObjectBean {
		/**
		 * package : Sign=WXPay
		 * out_trade_no : 201912301426284609k43z779E7gWZn9
		 * appid : wx77ff4c8528dac183
		 * sign : EC04B1611CA2339D31CE6D8BF2321515
		 * package1 : Sign=WXPay
		 * partnerid : 1567167441
		 * prepayid : wx301426287091205ba30e6c981036260500
		 * noncestr : 7eRrMA6b9yGRLe4giy0ftZzmfMg1gPZy
		 * timestamp : 1577687188
		 */

		@SerializedName("package")
		private String packageX;
		private String out_trade_no;
		private String appid;
		private String sign;
		private String package1;
		private String partnerid;
		private String prepayid;
		private String noncestr;
		private String timestamp;

		public String getPackageX() {
			return packageX;
		}

		public void setPackageX(String packageX) {
			this.packageX = packageX;
		}

		public String getOut_trade_no() {
			return out_trade_no;
		}

		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public String getPackage1() {
			return package1;
		}

		public void setPackage1(String package1) {
			this.package1 = package1;
		}

		public String getPartnerid() {
			return partnerid;
		}

		public void setPartnerid(String partnerid) {
			this.partnerid = partnerid;
		}

		public String getPrepayid() {
			return prepayid;
		}

		public void setPrepayid(String prepayid) {
			this.prepayid = prepayid;
		}

		public String getNoncestr() {
			return noncestr;
		}

		public void setNoncestr(String noncestr) {
			this.noncestr = noncestr;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
	}
}
