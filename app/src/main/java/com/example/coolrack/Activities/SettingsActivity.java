package com.example.coolrack.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.GenerateBooks;


public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        setTitle(R.string.title_activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat
            implements Preference.OnPreferenceChangeListener {
        private DropDownPreference seachBooks,lenguageMenu, info;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            seachBooks = findPreference("seachBooks");
            lenguageMenu = findPreference("lenguageMenu");
            info = findPreference("goToInfo");

            lenguageMenu.setOnPreferenceChangeListener(this);

            seachBooks.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new GenerateBooks(getActivity()).searchLibors();
                    return true;
                }
            });

            info.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.i(getTag(),"GO FROM INCORMACION ACTIVITY");
                    startActivity(new Intent(getActivity(),com.example.coolrack.Activities.InformacionActivity.class));
                    return false;
                }
            });



        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference.getKey().equals("lenguageMenu")) {



                String languageCode = (String) newValue;


                // Guarda la configuración del idioma seleccionado en las preferencias compartidas de la aplicación
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("language", languageCode);
                editor.apply();

                Intent intent = requireActivity().getIntent();
                requireActivity().finish();
                requireActivity().overridePendingTransition(0, 0);
                startActivity(intent);
                requireActivity().overridePendingTransition(0, 0);

                // Reinicia la actividad para aplicar el nuevo idioma seleccionado
                requireActivity().recreate();
            }
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}