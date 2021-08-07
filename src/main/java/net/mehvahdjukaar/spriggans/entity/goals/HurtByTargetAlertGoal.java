package net.mehvahdjukaar.spriggans.entity.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class HurtByTargetAlertGoal extends HurtByTargetGoal {
    public HurtByTargetAlertGoal(CreatureEntity p_i50317_1_, Class<?>... p_i50317_2_) {
        super(p_i50317_1_, p_i50317_2_);
    }

    public HurtByTargetGoal setCallForHelp(Class<?>... toHelp) {
        this.toCallForAid = toHelp;
        return this;
    }

    private Class<?>[] toCallForAid = null;

    protected void alertOthers() {
        super.alertOthers();
        double d0 = this.getFollowDistance();
        AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(this.mob.position()).inflate(d0, 10.0D, d0);
        for(Class c : this.toCallForAid) {

            List<MobEntity> list = this.mob.level.getLoadedEntitiesOfClass(c, axisalignedbb);
            for(MobEntity e : list){
                if (e.getTarget() == null && (!(this.mob instanceof TameableEntity) || ((TameableEntity)this.mob).getOwner() == ((TameableEntity)e).getOwner()) && !e.isAlliedTo(this.mob.getLastHurtByMob())) {
                    this.alertOther(e, this.mob.getLastHurtByMob());
                }
            }



        }
    }
}
