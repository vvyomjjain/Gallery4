package com.example.vyomjain.gallery4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class FolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        final String[] paths = getIntent().getStringArrayExtra("paths");
        getSupportActionBar().setTitle("Folders");

        List<String> folders = new ArrayList<String>();
        List<String> firstImages = new ArrayList<String>();

        for(int i=0; i<paths.length; i++){
            File file = new File(paths[i]);
            String directoryPath = file.getParent();
            File directory = new File(directoryPath);

            if(!folders.contains(directory.getName())){
                folders.add(directory.getName());
                firstImages.add(paths[i]);
            }
        }

        final String[] folderArray = folders.toArray(new String[folders.size()]);
        String[] imageArray = firstImages.toArray(new String[firstImages.size()]);

        GridView folderGrid = (GridView)findViewById(R.id.folder_grid);
        folderGrid.setAdapter(new FolderAdapter(FolderActivity.this, folderArray, imageArray));

        folderGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent singleFolderIntent = new Intent(FolderActivity.this, SingleFolder.class);
                singleFolderIntent.putExtra("paths", paths);
                singleFolderIntent.putExtra("folderName", folderArray[position]);
                startActivity(singleFolderIntent);
            }
        });
    }
}
