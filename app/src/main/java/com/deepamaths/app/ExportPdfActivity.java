package com.deepamaths.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportPdfActivity extends AppCompatActivity {

    private EditText etPdfContent;
    private Button btnExportPdf, btnPdfBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_pdf);

        etPdfContent = findViewById(R.id.etPdfContent);
        btnExportPdf = findViewById(R.id.btnExportPdf);
        btnPdfBack = findViewById(R.id.btnPdfBack);

        // PDF உருவாக்கும் பட்டன்
        btnExportPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etPdfContent.getText().toString().trim();

                if (content.isEmpty()) {
                    Toast.classCastExceptionMsg("தயவுசெய்து குறிப்புகளை உள்ளிடவும்!"); // Simple toast below
                    Toast.makeText(ExportPdfActivity.this, "தயவுசெய்து குறிப்புகளை உள்ளிடவும்!", Toast.LENGTH_SHORT).show();
                    return;
                }

                createPdf(content);
            }
        });

        // பின் செல்ல (Back)
        btnPdfBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createPdf(String textContent) {
        // 1. புதிய PdfDocument உருவாக்குதல்
        PdfDocument pdfDocument = new PdfDocument();

        // 2. பக்கத்தின் அளவு (Page setup - A4 size standard: 595 x 842 pixels)
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // 3. கேன்வாஸ் மற்றும் பெயிண்ட் அமைப்பு (Canvas & Paint)
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(16);
        paint.setColor(android.graphics.Color.BLACK);

        // உரை வரிகளை அச்சிடுதல்
        int x = 40;
        int y = 60;
        
        // தலைப்பு
        Paint titlePaint = new Paint();
        titlePaint.setTextSize(22);
        titlePaint.setFakeBoldText(true);
        titlePaint.setColor(android.graphics.Color.BLUE);
        canvas.drawText("Deepa Maths - Study Notes", x, y, titlePaint);
        
        y += 40;
        
        // பயனர் உள்ளிட்ட உரை (Multi-line text handling)
        for (String line : textContent.split("\n")) {
            canvas.drawText(line, x, y, paint);
            y += 30; // அடுத்த வரிக்கு இடைவெளி
        }

        // 4. பக்கத்தை முடித்தல்
        pdfDocument.finishPage(page);

        // 5. ஃபைலை சேமிக்கும் இடம் (App Documents Directory)
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "DeepaMaths_Notes.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF வெற்றிகரமாக சேமிக்கப்பட்டது!\nஇடம்: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "PDF சேமிப்பதில் பிழை: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // 6. டாக்குமெண்டை மூடுதல்
        pdfDocument.close();
    }
}
