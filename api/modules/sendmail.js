const nodemailer = require('nodemailer');

const sendMail = async (toAccount, { subject, text, html }, logger = true) => {
    let transporter = nodemailer.createTransport({
        service: 'Gmail',
        auth: {
            user: process.env.EMAIL,
            pass: process.env.PASS,
        },
        logger,
    });
    let info = await transporter.sendMail({
        from: `"${process.env.NAME}" <${process.env.EMAIL}>`,
        to: toAccount,
        subject,
        text,
        html,
    });

    console.log('Message sent: %s', info.messageId);
};

module.exports = sendMail;
