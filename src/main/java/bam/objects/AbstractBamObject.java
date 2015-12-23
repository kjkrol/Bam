package bam.objects;

import lombok.Data;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.opengl.Texture;

import java.util.Optional;

/**
 * @author Karol Krol
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public abstract class AbstractBamObject implements ControllableBamObject {

    public static final FixtureDef DEFAULT_FIXTURE_DEF = new FixtureDef();

    static {
        DEFAULT_FIXTURE_DEF.density = 1.0f;
        DEFAULT_FIXTURE_DEF.friction = 0.7f;
        DEFAULT_FIXTURE_DEF.restitution = 0.5f;
    }

    protected final Body body;

    protected final Texture texture;

    protected final ReadableColor color;

    protected abstract void drawTexture();

    protected abstract void drawShape();

    public void draw() {

        GL11.glLoadIdentity();
        GL11.glTranslatef(getXPos(), getYPos(), 0);

        float angle = (float) (body.getAngle() * 180 / Math.PI);
        GL11.glRotatef(angle, 0, 0, 1);
        ReadableColor color1 = null != this.color ? color : ReadableColor.WHITE;
        GL11.glColor3f(color1.getRed(), color1.getGreen(), color1.getBlue());

        if(null != this.texture) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            this.texture.bind();
            this.drawTexture();
        } else {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            this.drawShape();
        }

    }

    public float getXPos() {
        return body.getPosition().x;
    }

    public float getYPos() {
        return body.getPosition().y;
    }


}