package xyz.girudo.jetset.controllers;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.log.RealmLog;
import xyz.girudo.jetset.R;
import xyz.girudo.jetset.entities.HomeMenu;
import xyz.girudo.jetset.entities.LeftMenu;
import xyz.girudo.jetset.entities.LeftMenuHeader;
import xyz.girudo.jetset.entities.Offer;

/**
 * Created by Novyandi Nurahmad on 11/20/16
 */

public class RealmDataControl {
    private static RealmDataControl realmDataControl;
    private Realm realm;
    private RealmConfiguration realmConfiguration;
    private Context context;

    private RealmDataControl(Context context) {
        this.context = context;
        initRealm();
        clearRealm();
    }

    public static  RealmDataControl getInstance(Context context) {
        if (realmDataControl == null)
            realmDataControl = new RealmDataControl(context);
        return realmDataControl;
    }

    public Realm getRealm() {
        if (realm == null) initRealm();
        return realm;
    }

    private void initRealm() {
        Realm.init(context);
        Realm.setDefaultConfiguration(getRealmConfig());
        RealmLog.add(new RealmDebugLogger());
        this.realm = Realm.getInstance(getRealmConfig());
    }

//    private String getDB(Context context) {
//        if (isExternalStorageWritable()) {
//            return Config.FILES_PATH;
//        } else {
//            return context.getFilesDir().getAbsolutePath();
//        }
//    }
//
//    private static boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        return Environment.MEDIA_MOUNTED.equals(state) && Environment.getExternalStorageDirectory()
//                .canWrite();
//    }

    private RealmConfiguration getRealmConfig() {
        if (realmConfiguration == null)
            realmConfiguration = new RealmConfiguration.Builder()
                    .directory(new File(context.getFilesDir().getAbsolutePath()))
                    .deleteRealmIfMigrationNeeded()
                    .schemaVersion(1)
                    .name(Config.DATABASE_NAME).build();
        return realmConfiguration;
    }

    public void clearRealm() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public boolean prepareAllData() {
        try {
            setLeftMenuHeaderData();
            setLeftMenuItemData();
            setOfferData();
            setHomeMenuData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setLeftMenuItemData() {
        int[] menuTitle = new int[]{
                R.string.ls_txt_newArrivals,
                R.string.ls_txt_mens,
                R.string.ls_txt_womens,
                R.string.ls_txt_kids,
                R.string.ls_txt_cart,
                R.string.ls_txt_myAccount,
                R.string.ls_txt_about,
                R.string.ls_txt_contact,
                R.string.ls_txt_logout};
        List<LeftMenu> leftMenus = new ArrayList<>();
        for (int aMenuTitle : menuTitle) {
            LeftMenu menu = new LeftMenu();
            menu.setTitle(context.getString(aMenuTitle));
            leftMenus.add(menu);
        }
        realm.beginTransaction();
        realm.copyToRealm(leftMenus);
        realm.commitTransaction();
    }

    public List<LeftMenu> getLeftMenuItemData() {
        return realm.where(LeftMenu.class).findAll();
    }

    private void setLeftMenuHeaderData() {
        LeftMenuHeader leftMenuHeader = new LeftMenuHeader();
        leftMenuHeader.setImage("http://lorempixel.com/100/100/people/1");
        leftMenuHeader.setName("Alvyan Damharun");

        realm.beginTransaction();
        realm.copyToRealm(leftMenuHeader);
        realm.commitTransaction();
    }

    public LeftMenuHeader getLeftMenuHeaderData() {
        return realm.where(LeftMenuHeader.class).findFirst();
    }

    private void setOfferData() {
        List<Offer> offers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Offer offer = new Offer();
            offer.setImageOffer("http://lorempixel.com/400/300/people/" + (i + 1));
            offers.add(offer);
        }
        realm.beginTransaction();
        realm.copyToRealm(offers);
        realm.commitTransaction();
    }

    public List<Offer> getOfferData() {
        return realm.where(Offer.class).findAll();
    }

    private void setHomeMenuData() {
        int[] menuTitle = new int[]{
                R.string.ls_txt_newArrivals,
                R.string.ls_txt_mens,
                R.string.ls_txt_womens,
                R.string.ls_txt_kids};
        List<HomeMenu> homeMenus = new ArrayList<>();
        int id = 0;
        for (int aMenuTitle : menuTitle) {
            HomeMenu homeMenu = new HomeMenu();
            homeMenu.setImage("http://lorempixel.com/400/300/people/" + (id + 1));
            homeMenu.setTitle(context.getString(aMenuTitle));
            homeMenus.add(homeMenu);
            id++;
        }
        realm.beginTransaction();
        realm.copyToRealm(homeMenus);
        realm.commitTransaction();
    }

    public List<HomeMenu> getHomeMenuData() {
        return realm.where(HomeMenu.class).findAll();
    }
}
