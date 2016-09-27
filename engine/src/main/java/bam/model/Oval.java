package bam.model;

import bam.OpenGlModelParams;
import bam.model.base.BaseModel;
import org.jbox2d.dynamics.Body;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

import static java.util.Objects.nonNull;

public class Oval extends BaseModel {

    private static final int MINIMUM_EDGES_NUMBER = 12;
    private static final float HALF = 0.5f;
    private static final int FACTOR_FOUR = 4;

    private final float radius;
    private final int numberOfEdges;
    private final float[] x;
    private final float[] y;
    private final float[] tx;
    private final float[] ty;

    public Oval(OpenGlModelParams openGlModelParams) {
        this(openGlModelParams.getBody(), openGlModelParams.getColor(), openGlModelParams.getParams()[0]);
    }

    private Oval(Body body, ReadableColor color, float radius) {
        super(body, color);
        this.radius = radius;
        int initEdges = (int) (radius / FACTOR_FOUR);
        this.numberOfEdges = initEdges < MINIMUM_EDGES_NUMBER ? MINIMUM_EDGES_NUMBER : initEdges;
        this.x = new float[numberOfEdges];
        this.y = new float[numberOfEdges];
        this.tx = new float[numberOfEdges];
        this.ty = new float[numberOfEdges];
        this.init();
    }

    @Override
    protected void drawShape() {

        GL11.glPointSize(FACTOR_FOUR);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(0, radius);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(0, radius);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int index = 0; index < numberOfEdges; ++index) {
            GL11.glVertex2f(x[index], y[index]);
        }
        GL11.glEnd();

        final ReadableColor color = getColor();
        GL11.glBegin(GL11.GL_POLYGON);
        if (nonNull(color)) {
            GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), HALF);
        }
        for (int index = 0; index < numberOfEdges; ++index) {
            GL11.glVertex2f(x[index], y[index]);
        }

        GL11.glEnd();
    }

    private void init() {
        final double radianFactor = 2 * Math.PI / this.numberOfEdges;
        for (int index = 0; index < this.numberOfEdges; ++index) {
            double radian = radianFactor * index;
            float xcos = (float) Math.cos(radian);
            float ysin = (float) Math.sin(radian);
            x[index] = xcos * radius;
            y[index] = ysin * radius;
            tx[index] = xcos * HALF + HALF;
            ty[index] = ysin * HALF + HALF;
        }
    }
}
