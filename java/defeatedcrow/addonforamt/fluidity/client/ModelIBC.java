package defeatedcrow.addonforamt.fluidity.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelIBC extends ModelBase
{
  //fields
    ModelRenderer bottom;
    ModelRenderer top;
    ModelRenderer body;
    ModelRenderer cap;
    ModelRenderer cage;
  
  public ModelIBC()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      bottom = new ModelRenderer(this, 0, 0);
      bottom.addBox(-8F, 7F, -8F, 16, 1, 16);
      bottom.setRotationPoint(0F, 16F, 0F);
      bottom.setTextureSize(64, 32);
      bottom.mirror = true;
      setRotation(bottom, 0F, 0F, 0F);
      top = new ModelRenderer(this, 0, 0);
      top.addBox(-8F, -8F, -8F, 16, 1, 16);
      top.setRotationPoint(0F, 16F, 0F);
      top.setTextureSize(64, 32);
      top.mirror = true;
      setRotation(top, 0F, 0F, 0F);
      body = new ModelRenderer(this, 0, 0);
      body.addBox(-7.5F, -7F, -7.5F, 15, 14, 15);
      body.setRotationPoint(0F, 16F, 0F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      cap = new ModelRenderer(this, 0, 0);
      cap.addBox(-2F, -8.5F, -2F, 4, 1, 4);
      cap.setRotationPoint(0F, 16F, 0F);
      cap.setTextureSize(64, 32);
      cap.mirror = true;
      setRotation(cap, 0F, 0F, 0F);
      cage = new ModelRenderer(this, 0, 0);
      cage.addBox(-8F, -7F, -8F, 16, 14, 16);
      cage.setRotationPoint(0F, 16F, 0F);
      cage.setTextureSize(64, 32);
      cage.mirror = true;
      setRotation(cage, 0F, 0F, 0F);
  }
  
  public void renderBottom(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    bottom.render(f5);
    top.render(f5);
  }
  
  public void renderCage(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    cap.render(f5);
    cage.render(f5);
  }
  
  public void renderBody(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    body.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
