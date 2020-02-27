package com.trianguloy.adnihilation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * For when sharing or opening external urls/email/other
 */
class ExternalUtils {

    /**
     * Opens a link, shows error if can't open
     *
     * @param url  link to open
     * @param cntx base context
     */
    static void openLink(String url, Context cntx) {
        try {
            cntx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(cntx, R.string.error, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Shares a text (with subject)
     *
     * @param text    text to share
     * @param subject subject when sharing
     * @param cntx    base context
     */
    static void shareText(String text, String subject, Context cntx) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setType("text/plain");
        cntx.startActivity(Intent.createChooser(intent, cntx.getString(R.string.send)));
    }

    /**
     * Sends an email (opens the email app)
     *
     * @param subject email subject
     * @param email   who to send to
     * @param cntx    base context
     */
    static void sendEmail(String subject, String email, Context cntx) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        cntx.startActivity(Intent.createChooser(emailIntent, cntx.getString(R.string.send)));
    }
}
