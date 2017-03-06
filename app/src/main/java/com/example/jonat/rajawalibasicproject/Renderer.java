package com.example.jonat.rajawalibasicproject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Sphere;

import org.rajawali3d.renderer.RajawaliRenderer;
import org.rajawali3d.cameras.ArcballCamera;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;

/**
 * Created by jonat on 2017. 03. 03..
 */
public class Renderer extends RajawaliRenderer {

    private Context context;

    private PointLight pointLight1;
    private PointLight pointLight2;
    private Sphere sphere1;
    private Sphere sphere2;
    private Object3D mObjectGroup;

    private double[] light1Position = new double[]{-10, 0, 15};
    private double[] light2Position = new double[]{10, 0, 15};

    public Renderer(Context context) {
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    @Override
    protected void initScene() {
        pointLight1 = new PointLight();
        pointLight1.setColor(1.0f, 1.0f, 1.0f);
        pointLight1.setPower(5);
        pointLight1.setPosition(light1Position[0], light1Position[1], light1Position[2]);
        getCurrentScene().addLight(pointLight1);

        pointLight2 = new PointLight();
        pointLight2.setColor(1.0f, 1.0f, 1.0f);
        pointLight2.setPower(5);
        pointLight2.setPosition(light2Position[0], light2Position[1], light2Position[2]);
        getCurrentScene().addLight(pointLight2);

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        float[] a = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        material.setColor(a);

        sphere1 = new Sphere(1f, 24, 24);
        sphere1.setPosition(light1Position[0], light1Position[1], light1Position[2]);
        sphere1.setMaterial(material);
        getCurrentScene().addChild(sphere1);

        sphere2 = new Sphere(1f, 24, 24);
        sphere2.setPosition(light2Position[0], light2Position[1], light2Position[2]);
        sphere2.setMaterial(material);
        getCurrentScene().addChild(sphere2);

        /*Texture earthTexture = new Texture("Earth", R.drawable.earthtruecolor_nasa_big);
        try {
            material.addTexture(earthTexture);
        } catch (ATexture.TextureException error) {
            Log.d("DEBUG", "TEXTURE ERROR");
        }*/


        LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.kondor_zoltan_with_mouth_1_obj);
        try {
            objParser.parse();
            mObjectGroup = objParser.getParsedObject();
            mObjectGroup.setScale(0.25);
            mObjectGroup.setMaterial(material);
            getCurrentScene().addChild(mObjectGroup);
        } catch (ParsingException e) {
            e.printStackTrace();
        }

//        getCurrentCamera().setZ(16);
        ArcballCamera arcball = new ArcballCamera(mContext, ((Activity) mContext).findViewById(R.id.drawer_layout));
        arcball.setTarget(mObjectGroup); //your 3D Object
        arcball.setPosition(0, 0, 16); //optional

        getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);
    }

    @Override
    public void onOffsetsChanged(float v, float v1, float v2, float v3, int i, int i1) {

    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }

    /*@Override
    public void onRender(final long elapsedTime, final double deltaTime) {
        super.onRender(elapsedTime, deltaTime);
        sphere1.rotate(Vector3.Axis.Y, 1.0);
    }*/

}
