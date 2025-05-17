package net.justmili.underthestars.block;

import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WhiteSleepingBag extends BedBlock {

    public static final EnumProperty<BedPart> PART = EnumProperty.create("part", BedPart.class);
    public static final BooleanProperty OCCUPIED = BooleanProperty.create("occupied");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape BAG = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    protected static final VoxelShape NORTH_PILLOW = Block.box(2.0D, 2.0D, 1.0D, 14.0D, 3.0D, 7.0D);
    protected static final VoxelShape EAST_PILLOW = Block.box(9.0D, 2.0D, 2.0D, 15.0D, 3.0D, 14.0D);
    protected static final VoxelShape SOUTH_PILLOW = Block.box(2.0D, 2.0D, 9.0D, 14.0D, 3.0D, 15.0D);
    protected static final VoxelShape WEST_PILLOW = Block.box(1.0D, 2.0D, 2.0D, 7.0D, 3.0D, 14.0D);

    public WhiteSleepingBag() {
        super(DyeColor.WHITE, BlockBehaviour.Properties.copy(Blocks.WHITE_BED).sound(SoundType.WOOL));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, BedPart.FOOT)
                .setValue(OCCUPIED, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, OCCUPIED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        BedPart part = state.getValue(PART);
        VoxelShape bagShape;
        VoxelShape pillowShape;

        if (part == BedPart.HEAD) {
            switch (direction) {
                case NORTH:
                    bagShape = BAG;
                    pillowShape = NORTH_PILLOW;
                    break;
                case SOUTH:
                    bagShape = BAG;
                    pillowShape = SOUTH_PILLOW;
                    break;
                case WEST:
                    bagShape = BAG;
                    pillowShape = WEST_PILLOW;
                    break;
                default:
                    bagShape = BAG;
                    pillowShape = EAST_PILLOW;
                    break;
            }
        } else {
            switch (direction) {
                case NORTH:
                    bagShape = BAG;
                    pillowShape = Shapes.empty();
                    break;
                case SOUTH:
                    bagShape = BAG;
                    pillowShape = Shapes.empty();
                    break;
                case WEST:
                    bagShape = BAG;
                    pillowShape = Shapes.empty();
                    break;
                default:
                    bagShape = BAG;
                    pillowShape = Shapes.empty();
                    break;
            }
        }
        return Shapes.or(bagShape, pillowShape);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            Direction direction = state.getValue(FACING);
            BlockPos headPos = pos.relative(direction);
            BlockState headState = state.setValue(PART, BedPart.HEAD);
            level.setBlock(headPos, headState, 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide && player.isShiftKeyDown()) {
            Direction direction = state.getValue(FACING);
            BedPart part = state.getValue(PART);
            BlockPos footPos = (part == BedPart.FOOT) ? pos : pos.relative(direction.getOpposite());
            BlockPos headPos = footPos.relative(direction);

            world.removeBlock(headPos, false);
            world.removeBlock(footPos, false);

            // Add to player inventory instead of dropping
            ItemStack bagStack = new ItemStack(this);
            player.getInventory().add(bagStack);

            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }
}
