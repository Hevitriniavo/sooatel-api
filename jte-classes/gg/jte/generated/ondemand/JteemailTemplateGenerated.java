package gg.jte.generated.ondemand;
public final class JteemailTemplateGenerated {
	public static final String JTE_NAME = "emailTemplate.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,4,4,4,4,8,8,8,61,61,61,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, com.fresh.coding.sooatelapi.entities.OtpCode data) {
		jteOutput.writeContent("\n<div class=\"email-container\">\n    <div class=\"email-header\">\n        <h1>Bonjour ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(data.getUser().getName());
		jteOutput.writeContent(",</h1>\n        <p>Voici votre code OTP pour la récupération de mot de passe :</p>\n    </div>\n    <div class=\"email-body\">\n        <h2 class=\"otp-code\">");
		jteOutput.setContext("h2", null);
		jteOutput.writeUserContent(data.getOtpCode());
		jteOutput.writeContent("</h2>\n        <p>Votre code est valable pour la prochaine minute.</p>\n    </div>\n    <div class=\"email-footer\">\n        <p style=\"font-size: 12px; color: #999;\">Si vous n'avez pas demandé ce changement, veuillez ignorer cet email.</p>\n    </div>\n</div>\n\n<style>\n    .email-container {\n        width: 100%;\n        max-width: 600px;\n        margin: 0 auto;\n        background-color: #f8f8f8;\n        padding: 20px;\n        border-radius: 8px;\n        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n        font-family: Arial, sans-serif;\n    }\n\n    .email-header {\n        text-align: center;\n        margin-bottom: 20px;\n        background-color: #333;\n        padding: 10px;\n        border-radius: 5px;\n        color: #fff;\n    }\n\n    .email-header h1 {\n        color: #ffa500;\n    }\n\n    .email-body {\n        text-align: center;\n        margin-bottom: 20px;\n        background-color: #e0e0e0;\n        padding: 20px;\n        border-radius: 5px;\n    }\n\n    .otp-code {\n        color: #ff7f50;\n        font-size: 24px;\n        font-weight: bold;\n    }\n\n    .email-footer {\n        text-align: center;\n        font-size: 12px;\n        color: #999;\n    }\n</style>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		com.fresh.coding.sooatelapi.entities.OtpCode data = (com.fresh.coding.sooatelapi.entities.OtpCode)params.get("data");
		render(jteOutput, jteHtmlInterceptor, data);
	}
}
