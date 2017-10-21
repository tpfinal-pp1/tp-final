package com.TpFinal.view.component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.*;
import org.tepi.imageviewer.ImageViewer;
import org.tepi.imageviewer.ImageViewer.ImageSelectionListener;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;

@SuppressWarnings("serial")
@Theme("demotheme")
public class ImageVisualizer extends Window {




    private ImageViewer imageViewer;
    private VerticalLayout mainLayout;
    private TextField selectedImage = new TextField();


    public ImageVisualizer (){
        super();
        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);


        imageViewer = new ImageViewer();
        imageViewer.setSizeFull();
        imageViewer.setAnimationEnabled(false);
        imageViewer.setSideImageRelativeWidth(0.7f);

        imageViewer.addListener((ImageSelectionListener) e -> {
            selectedImage.setValue(e.getSelectedImageIndex() >= 0 ? String.valueOf(e.getSelectedImageIndex()) : "-");
        });
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeUndefined();
        hl.setMargin(false);
        hl.setSpacing(true);

        mainLayout.addComponent(hl);
        mainLayout.addComponent(imageViewer);
        mainLayout.setExpandRatio(imageViewer, 1);

        Layout ctrls = createControls();
        mainLayout.addComponent(ctrls);
        mainLayout.setComponentAlignment(ctrls, Alignment.BOTTOM_CENTER);


        imageViewer.setAnimationEnabled(true);
        setContent(mainLayout);

        imageViewer.focus();
        UI.getCurrent().addWindow(this);
        this.setResizable(false);
        this.setSizeFull();
        this.center();
    }






    private Layout createControls() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeUndefined();
        hl.setMargin(false);
        hl.setSpacing(true);
        imageViewer.setHiLiteEnabled(true);

        CheckBox c = new CheckBox("HiLite");
        c.addValueChangeListener(e -> {
            imageViewer.setHiLiteEnabled(e.getValue());
            imageViewer.focus();
        });

        c.setValue(true);
     //   hl.addComponent(c);
     //   hl.setComponentAlignment(c, Alignment.BOTTOM_CENTER);

        c = new CheckBox("Animate");
        c.addValueChangeListener(e -> {
            imageViewer.setAnimationEnabled(e.getValue());
            imageViewer.focus();
            });


        c.setValue(true);
     //   hl.addComponent(c);
    //   hl.setComponentAlignment(c, Alignment.BOTTOM_CENTER);

        Slider s = new Slider("Animation duration (ms)");
        s.setMax(2000);
        s.setMin(200);
        s.setWidth("120px");
        s.addValueChangeListener(e -> {
            imageViewer.setAnimationDuration((int) Math.round(e.getValue()));
            imageViewer.focus();
        });
        s.setValue(350d);
     //   hl.addComponent(s);
     //   hl.setComponentAlignment(s, Alignment.BOTTOM_CENTER);

        s = new Slider("Center image width");
        s.setResolution(2);
        s.setMax(1);
        s.setMin(0.1);
        s.setWidth("120px");
        s.addValueChangeListener(e -> {
            imageViewer.setCenterImageRelativeWidth(e.getValue().floatValue());
            imageViewer.focus();
        });
        s.setValue(0.55);
      //  hl.addComponent(s);
      //  hl.setComponentAlignment(s, Alignment.BOTTOM_CENTER);

        s = new Slider("Side image count");
        s.setMax(5);
        s.setMin(1);
        s.setWidth("120px");

        s.addValueChangeListener(e -> {
            imageViewer.setSideImageCount((int) Math.round(e.getValue()));
            imageViewer.focus();
        });
        s.setValue(2d);
      //  hl.addComponent(s);
      //  hl.setComponentAlignment(s, Alignment.BOTTOM_CENTER);

        s = new Slider("Side image width");
        s.setResolution(2);
        s.setMax(0.8);
        s.setMin(0.5);
        s.setWidth("120px");

        s.addValueChangeListener(e -> {
            imageViewer.setSideImageRelativeWidth(e.getValue().floatValue());
            imageViewer.focus();
        });

        s.setValue(0.65);
     //   hl.addComponent(s);
       // hl.setComponentAlignment(s, Alignment.BOTTOM_CENTER);

        s = new Slider("Horizontal padding");
        s.setMax(10);
        s.setMin(0);
        s.setWidth("120px");

        s.addValueChangeListener(e -> {
            imageViewer.setImageHorizontalPadding((int) Math.round(e.getValue()));
            imageViewer.focus();
        });
        s.setValue(1d);
      //  hl.addComponent(s);
       // hl.setComponentAlignment(s, Alignment.BOTTOM_CENTER);

        s = new Slider("Vertical padding");
        s.setMax(10);
        s.setMin(0);
        s.setWidth("120px");
        s.addValueChangeListener(e -> {
            imageViewer.setImageVerticalPadding((int) Math.round(e.getValue()));
            imageViewer.focus();
        });
        s.setValue(5d);
       // hl.addComponent(s);
       // hl.setComponentAlignment(s, Alignment.BOTTOM_CENTER);

      //  selectedImage.setWidth("50px");
       // hl.addComponent(selectedImage);
     //   hl.setComponentAlignment(selectedImage, Alignment.BOTTOM_CENTER);

        return hl;
    }

    /**
     * Creates a list of Resources to be shown in the ImageViewer.
     *
     * @return List of Resource instances
     */
    private void createDemoList() {

        List<Resource> img = new ArrayList<Resource>();
        for (int i = 1; i < 10; i++) {
            img.add(new ThemeResource("img"+ File.separator+ "casa.jpg"));
        }
        imageViewer.setImages(img);
    }

   public void singleImage(Resource sr) {

        List<Resource> img = new ArrayList<Resource>();

            img.add(sr);
        imageViewer.setImages(img);
       imageViewer.setCenterImageIndex(0);

    }


    /*
    private List<Resource> createImageListFromFolder(String Folder) {

        List<Resource> img = new ArrayList<Resource>();
        for (int i = 1; i < 10; i++) {
            img.add(new ThemeResource("images/" + i + ".jpg"));
        }
        return img;
    }*/

}