package messagesservice.data;

import java.io.Serializable;

public class Notification implements Serializable {
	private static final long serialVersionUID = -4543971047732329210L;

	private Type type;
	private String text;
//	private String[] params;

	public Notification(String text) {
		this.text = text;
//		this.params = params;

		String[] splitedText = text.split("\\.");
		int i = 0;
		for (; i < splitedText.length; i++) {
			if (splitedText[i].equals("notification")) {
				i++;
				break;
			}
		}
		if (i == splitedText.length) {
			throw new RuntimeException(text + " have to include pattern as \"notification\".\"notification type\"");
		}
		
		String typeStr = splitedText[i].toUpperCase();
		this.type = Type.valueOf(typeStr);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

//	public String[] getParams() {
//		return params;
//	}
//
//	public boolean isParams() {
//		if (params.length > 0) {
//			return true;
//		}
//
//		return false;
//	}

	public enum Type {
		SUCCESS("SUCCESS"), 
		WARNING("WARNING"), 
		ERROR("ERROR"),
		INFO("INFO");

		String type;

		private Type(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return type;
		}
	}
}
