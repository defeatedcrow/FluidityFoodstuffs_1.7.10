package defeatedcrow.addonforamt.fluidity.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelFluidHopper extends ModelBase
{
  //fields
    ModelRenderer sideF;
    ModelRenderer sideB;
    ModelRenderer sideR;
    ModelRenderer sideL;
    ModelRenderer bottom1;
    ModelRenderer bottom2;
    ModelRenderer bottom3;
    ModelRenderer drain;
    ModelRenderer valve1;
    ModelRenderer valve2;
    ModelRenderer valve3;
    
    //for adv
    ModelRenderer valve4;
    ModelRenderer valve5;
  
  public ModelFluidHopper()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      sideF = new ModelRenderer(this, 0, 0);
      sideF.addBox(-7F, -8F, -8F, 14, 8, 1);
      sideF.setRotationPoint(0F, 16F, 0F);
      sideF.setTextureSize(64, 32);
      sideF.mirror = true;
      setRotation(sideF, 0F, 0F, 0F);
      sideB = new ModelRenderer(this, 0, 0);
      sideB.addBox(-7F, -8F, 7F, 14, 8, 1);
      sideB.setRotationPoint(0F, 16F, 0F);
      sideB.setTextureSize(64, 32);
      sideB.mirror = true;
      setRotation(sideB, 0F, 0F, 0F);
      sideR = new ModelRenderer(this, 30, 0);
      sideR.addBox(-8F, -8F, -8F, 16, 8, 1);
      sideR.setRotationPoint(0F, 16F, 0F);
      sideR.setTextureSize(64, 32);
      sideR.mirror = true;
      setRotation(sideR, 0F, 1.570796F, 0F);
      sideL = new ModelRenderer(this, 30, 0);
      sideL.addBox(-8F, -8F, 7F, 16, 8, 1);
      sideL.setRotationPoint(0F, 16F, 0F);
      sideL.setTextureSize(64, 32);
      sideL.mirror = true;
      setRotation(sideL, 0F, 1.570796F, 0F);
      bottom1 = new ModelRenderer(this, 0, 10);
      bottom1.addBox(-7F, 0F, -7F, 14, 1, 14);
      bottom1.setRotationPoint(0F, 16F, 0F);
      bottom1.setTextureSize(64, 32);
      bottom1.mirror = true;
      setRotation(bottom1, 0F, 0F, 0F);
      bottom2 = new ModelRenderer(this, 0, 10);
      bottom2.addBox(-5F, 1F, -5F, 10, 1, 10);
      bottom2.setRotationPoint(0F, 16F, 0F);
      bottom2.setTextureSize(64, 32);
      bottom2.mirror = true;
      setRotation(bottom2, 0F, 0F, 0F);
      bottom3 = new ModelRenderer(this, 0, 10);
      bottom3.addBox(-3F, 2F, -3F, 6, 1, 6);
      bottom3.setRotationPoint(0F, 16F, 0F);
      bottom3.setTextureSize(64, 32);
      bottom3.mirror = true;
      setRotation(bottom3, 0F, 0F, 0F);
      drain = new ModelRenderer(this, 0, 10);
      drain.addBox(-1.5F, 3F, -1.5F, 3, 5, 3);
      drain.setRotationPoint(0F, 16F, 0F);
      drain.setTextureSize(64, 32);
      drain.mirror = true;
      setRotation(drain, 0F, 0F, 0F);
      valve1 = new ModelRenderer(this, 0, 26);
      valve1.addBox(-2F, 4F, -2F, 4, 2, 4);
      valve1.setRotationPoint(0F, 16F, 0F);
      valve1.setTextureSize(64, 32);
      valve1.mirror = true;
      setRotation(valve1, 0F, 0F, 0F);
      valve2 = new ModelRenderer(this, 17, 26);
      valve2.addBox(2F, 4.5F, -0.5F, 2, 1, 1);
      valve2.setRotationPoint(0F, 16F, 0F);
      valve2.setTextureSize(64, 32);
      valve2.mirror = true;
      setRotation(valve2, 0F, 0F, 0F);
      valve3 = new ModelRenderer(this, 28, 25);
      valve3.addBox(2.5F, -1F, -1F, 1, 2, 5);
      valve3.setRotationPoint(0F, 21F, 0F);
      valve3.setTextureSize(64, 32);
      valve3.mirror = true;
      setRotation(valve3, 0F, 0F, 0F);
      
      valve4 = new ModelRenderer(this, 17, 26);
      valve4.addBox(-1F, 4F, -1F, 2, 2, 2);
      valve4.setRotationPoint(3.5F, 16F, 0F);
      valve4.setTextureSize(64, 32);
      valve4.mirror = true;
      setRotation(valve4, 0F, 0F, 0F);
      valve5 = new ModelRenderer(this, 28, 25);
      valve5.addBox(2F, -1.5F, -2F, 2, 3, 4);
      valve5.setRotationPoint(0F, 21F, 0F);
      valve5.setTextureSize(64, 32);
      valve5.mirror = true;
      setRotation(valve5, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    sideF.render(f5);
    sideB.render(f5);
    sideR.render(f5);
    sideL.render(f5);
    bottom1.render(f5);
    bottom2.render(f5);
    bottom3.render(f5);
    drain.render(f5);
    valve1.render(f5);
    valve2.render(f5);
    
  }
  
  public void renderValve(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    valve3.render(f5);
  }
  
  public void renderSolenoidValve(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    valve4.render(f5);
    valve5.render(f5);
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
    valve3.rotateAngleX = f3 / (180F / (float)Math.PI);
    valve4.rotateAngleY = f3 / (180F / (float)Math.PI);
  }

}
