@param com.fresh.coding.sooatelapi.entities.OtpCode data

<div class="email-container">
    <div class="email-header">
        <h1>Bonjour ${data.getUser().getName()},</h1>
        <p>Voici votre code OTP pour la récupération de mot de passe :</p>
    </div>
    <div class="email-body">
        <h2 class="otp-code">${data.getOtpCode()}</h2>
        <p>Votre code est valable pour la prochaine minute.</p>
    </div>
    <div class="email-footer">
        <p style="font-size: 12px; color: #999;">Si vous n'avez pas demandé ce changement, veuillez ignorer cet email.</p>
    </div>
</div>

<style>
    .email-container {
        width: 100%;
        max-width: 600px;
        margin: 0 auto;
        background-color: #f8f8f8;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        font-family: Arial, sans-serif;
    }

    .email-header {
        text-align: center;
        margin-bottom: 20px;
        background-color: #333;
        padding: 10px;
        border-radius: 5px;
        color: #fff;
    }

    .email-header h1 {
        color: #ffa500;
    }

    .email-body {
        text-align: center;
        margin-bottom: 20px;
        background-color: #e0e0e0;
        padding: 20px;
        border-radius: 5px;
    }

    .otp-code {
        color: #ff7f50;
        font-size: 24px;
        font-weight: bold;
    }

    .email-footer {
        text-align: center;
        font-size: 12px;
        color: #999;
    }
</style>
