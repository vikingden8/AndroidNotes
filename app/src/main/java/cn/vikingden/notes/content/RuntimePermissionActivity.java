package cn.vikingden.notes.content;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.vikingden.notes.R;
import cn.vikingden.notes.content.runtime.RuntimePermissionsFragment;
import cn.vikingden.notes.content.runtime.camera.CameraPreviewFragment;
import cn.vikingden.notes.content.runtime.contacts.ContactsFragment;

/**
 * Created by Viking Den on 2017/8/2.
 */
/**
 * Launcher Activity that demonstrates the use of runtime permissions for Android M.
 * It contains a summary sample description, sample log and a Fragment that calls callbacks on this
 * Activity to illustrate parts of the runtime permissions API.
 * <p>
 * This Activity requests permissions to access the camera ({@link android.Manifest.permission#CAMERA})
 * when the 'Show Camera' button is clicked to display the camera preview.
 * Contacts permissions (({@link android.Manifest.permission#READ_CONTACTS} and ({@link
 * android.Manifest.permission#WRITE_CONTACTS})) are requested when the 'Show and Add Contacts'
 * button is
 * clicked to display the first contact in the contacts database and to add a dummy contact
 * directly to it. Permissions are verified and requested through compat helpers in the support v4
 * library, in this Activity using {@link ActivityCompat}.
 * First, permissions are checked if they have already been granted through {@link
 * ActivityCompat#checkSelfPermission(Context, String)}.
 * If permissions have not been granted, they are requested through
 * {@link ActivityCompat#requestPermissions(Activity, String[], int)} and the return value checked
 * in
 * a callback to the {@link android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback}
 * interface.
 * <p>
 * Before requesting permissions, {@link ActivityCompat#shouldShowRequestPermissionRationale(Activity,
 * String)}
 * should be called to provide the user with additional context for the use of permissions if they
 * have been denied previously.
 * <p>
 * If this sample is executed on a device running a platform version below M, all permissions
 * declared
 * in the Android manifest file are always granted at install time and cannot be requested at run
 * time.
 * <p>
 * This sample targets the M platform and must therefore request permissions at runtime. Change the
 * targetSdk in the file 'Application/build.gradle' to 22 to run the application in compatibility
 * mode.
 * Now, if a permission has been disable by the system through the application settings, disabled
 * APIs provide compatibility data.
 * For example the camera cannot be opened or an empty list of contacts is returned. No special
 * action is required in this case.
 * <p>
 * (This class is based on the MainActivity used in the SimpleFragment sample template.)
 */
public class RuntimePermissionActivity extends AppCompatActivity {

    /**
     * Id to identify a camera permission request.
     */
    private static final int REQUEST_CAMERA = 0;

    /**
     * Id to identify a contacts permission request.
     */
    private static final int REQUEST_CONTACTS = 1;

    private View mLayout ;

    /**
     * Permissions required to read and write contacts. Used by the {@link ContactsFragment}.
     */
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    /**
     * Called when the 'show camera' button is clicked.
     * Callback is defined in resource layout definition.
     */
    public void showCamera(View view) {
        // BEGIN_INCLUDE(camera_permission)
        // Check if the Camera permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.

            requestCameraPermission();

        } else {

            // Camera permissions is already available, show the camera preview.
            showCameraPreview();
        }
        // END_INCLUDE(camera_permission)
    }

    /**
     * Display the {@link CameraPreviewFragment} in the content area if the required Camera
     * permission has been granted.
     */
    private void showCameraPreview() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sample_content_fragment, CameraPreviewFragment.newInstance())
                .addToBackStack("camera")
                .commit();
    }

    /**
     * Requests the Camera permission.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestCameraPermission() {
        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Snackbar.make(mLayout, R.string.permission_camera_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(RuntimePermissionActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        // END_INCLUDE(camera_permission_request)
    }

    /**
     * Called when the 'show camera' button is clicked.
     * Callback is defined in resource layout definition.
     */
    public void showContacts(View v) {
        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            requestContactsPermissions();
        } else {
            // Contact permissions have been granted. Show the contacts fragment.
            showContactDetails();
        }
    }

    /**
     * Display the {@link ContactsFragment} in the content area if the required contacts
     * permissions
     * have been granted.
     */
    private void showContactDetails() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sample_content_fragment, ContactsFragment.newInstance())
                .addToBackStack("contacts")
                .commit();
    }

    /**
     * Requests the Contacts permissions.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestContactsPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_CONTACTS)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            // Display a SnackBar with an explanation and a button to trigger the request.
            Snackbar.make(mLayout, R.string.permission_contacts_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(RuntimePermissionActivity.this, PERMISSIONS_CONTACT,
                                            REQUEST_CONTACTS);
                        }
                    })
                    .show();
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Snackbar.make(mLayout, R.string.permision_available_camera,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();
            }
            // END_INCLUDE(permission_result)

        } else if (requestCode == REQUEST_CONTACTS) {
            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (verifyPermissions(grantResults)) {
                // All required permissions have been granted, display contacts fragment.
                Snackbar.make(mLayout, R.string.permision_available_contacts,
                        Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                Snackbar.make(mLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_runtime_permissions);
        mLayout = findViewById(R.id.sample_main_layout);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RuntimePermissionsFragment fragment = new RuntimePermissionsFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    public void onBackClick() {
        getSupportFragmentManager().popBackStack();
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
