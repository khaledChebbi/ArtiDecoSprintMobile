/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;

import com.codename1.ui.Form;
;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;

import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.Produit;
import com.mycompany.Service.ServiceProduit;

import java.util.ArrayList;

/**
 *
 * @author MBM info
 */


public class SearchProduct {

    /**
     * This file was generated by
     * <a href="https://www.codenameone.com/">Codename One</a> for the purpose
     * of building native mobile applications using Java.
     */
    Form f;
    private Form current;
    private Resources theme;
    String url = "";
    //private Resources theme =UIManager.initFirstTheme("/theme");

    ImageViewer imageViewer;
    EncodedImage imgEncodedImg;
    URLImage urlImage;

    // Button btnOk = new Button("Ajouter");
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

    }

    // PDF.createPdft();
    public void start(String search) {
        if (current != null) {
            current.show();
            return;
        }
        //Form hi = new Form();
        Form hi = new Form("website", new FlowLayout(Component.CENTER, Component.CENTER));
        hi.getToolbar().addCommandToLeftBar("back", null, (ev) -> {
            Affichage a = new Affichage();
            a.getF().show();
        });

        hi.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        //hi.add(txtSearch);
        Button btnajout = new Button("Facebook");
        //  hi.add(new Label("Les Utilisateurs"));
        hi.show();

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/ArtiDeco/web/app_dev.php/api/produits/all");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            Container LesUtilisateurs = new Container(new BoxLayout(BoxLayout.X_AXIS));

            @Override
            public void actionPerformed(NetworkEvent evt) {
                LesUtilisateurs.setScrollableX(true);
                LesUtilisateurs.setScrollableY(false);
                ArrayList<Produit> users = new ServiceProduit().getListAct(new String(con.getResponseData()), search);
                for (Produit user : users) {
                    Container container = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
                    Container detailsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    detailsContainer.add(new Label(user.getTitre()));
                    detailsContainer.add(user.getDescription());
                    detailsContainer.add(user.getCouleur());
                    //detailsContainer.add(user.get);
                  
                    container.add(detailsContainer);
                    hi.add(container);
                    hi.refreshTheme();

                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

    public Form getF() {
        return f;
    }

}
