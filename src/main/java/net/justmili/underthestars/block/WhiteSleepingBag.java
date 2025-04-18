/**
 * BED BLOCK TEMPLATE BY JustDacia
 * 
 * DEV NOTE: This gotta get finished up later with block models and shit for all the colors
 */
package net.justmili.underthestars.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.model.Model;

// The name of your class must match the name of your java file and vice versa.
public class WhiteSleepingBag extends BedBlock {
	
	// This is for defining the BlockState properties
	
	// PART represents the two parts that make up a bed. the HEAD and FOOT part.
	// OCCUPIED represents when the bed is occupied, as in whenever someone is using it.
	// FACING represents what direction it's facing. NORTH, SOUTH, WEST, EAST.
	public static final EnumProperty<BedPart> PART = EnumProperty.create("part", BedPart.class);
	public static final BooleanProperty OCCUPIED = BooleanProperty.create("occupied");
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	/*  This is what each value in Block.box stands for
	 *  Block.box(x1, y1, z1, x2, y2, z2)
	 *  
	 *  Your first 3 values are your starting point values.
	 *  Your last 3 values are your ending point values.
	 *  
	 *  x1 = 0.0D starts from the left edge of your block
	 *  y1 = 3.0D starts from 3 pixels above the ground
	 *  z1 = 0.0D starts from the front edge of your block.
	 *  x2 = 16.0D ends at the right edge of your block. It spans from 0.0D to 16.OD which is the full width of your block.
	 *  y2 = 9.0D ends at 9 pixels above the ground. This makes the bounding box 6 pixels tall since we've started at 3.0D from y1
	 *  z2 = 16.0D ends at the back edge of your block. It spans from 0.0D to 16.0D which is the full depth of your block.
	 */
	
	// This is the main base of your bed.
	protected static final VoxelShape BASE = Block.box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D);
	
	// This renders the legs of both your head and foot bed blocks.
	protected static final VoxelShape LEG_NORTH_WEST = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D);
	protected static final VoxelShape LEG_SOUTH_WEST = Block.box(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D);
	protected static final VoxelShape LEG_NORTH_EAST = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D);
	protected static final VoxelShape LEG_SOUTH_EAST = Block.box(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D);
	
	// This simply defines shapes for each possible direction to be equal to that of the base.
	protected static final VoxelShape NORTH_SHAPE = Shapes.or(BASE);
	protected static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE);
	protected static final VoxelShape WEST_SHAPE = Shapes.or(BASE);
	protected static final VoxelShape EAST_SHAPE = Shapes.or(BASE);
	
	// This is a constructor which defines things like what properties your bed block has. Like its sounds, hardness, other attributes etc.
	// It also just sets default values for the tags it has.
	
	// P.S. This must match the name of your main class too.
	public WhiteSleepingBag() {
		super(DyeColor.WHITE, BlockBehaviour.Properties.copy(Blocks.WHITE_BED));
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(PART, BedPart.FOOT)
        	.setValue(OCCUPIED, false)
        	.setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	// This doesn't need to be included in here but I've added it anyways
	// What this does is it sets all of the block properties for the block. That way blockstates work.
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, PART, OCCUPIED);
	}
	
	// This overrides VoxelShape and uses the defined bounding boxes up in our code to put them together, basically like building blocks. Legos.
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		
		// We set these variables in order to differentiate between what direction the blocks are facing and what part they are.
		Direction direction = state.getValue(FACING);
		BedPart part = state.getValue(PART);
		
		VoxelShape baseBedShape;
		VoxelShape legsBedShape;
		
		// If our part variable equals BedPart.HEAD, it starts a switch statement, going through all possible scenarios (cases) until it finds one that fits. If it doesn't find one, it'll send out a default value instead.
		if (part == BedPart.HEAD) {
			switch (direction) {
				case NORTH:
					baseBedShape = NORTH_SHAPE;
					legsBedShape = Shapes.or(LEG_NORTH_WEST, LEG_NORTH_EAST);
					break;
				case SOUTH:
					baseBedShape = SOUTH_SHAPE;
					legsBedShape = Shapes.or(LEG_SOUTH_WEST, LEG_SOUTH_EAST);
					break;
				case WEST:
					baseBedShape = WEST_SHAPE;
					legsBedShape = Shapes.or(LEG_NORTH_WEST, LEG_SOUTH_WEST);
					break;
				default:
					baseBedShape = EAST_SHAPE;
					legsBedShape = Shapes.or(LEG_NORTH_EAST, LEG_SOUTH_EAST);
					break;
			}
		} else {
			switch (direction) {
				case NORTH:
					baseBedShape = NORTH_SHAPE;
					legsBedShape = Shapes.or(LEG_SOUTH_WEST, LEG_SOUTH_EAST);
					break;
				case SOUTH:
					baseBedShape = SOUTH_SHAPE;
					legsBedShape = Shapes.or(LEG_NORTH_WEST, LEG_NORTH_EAST);
					break;
				case WEST:
					baseBedShape = WEST_SHAPE;
					legsBedShape = Shapes.or(LEG_SOUTH_EAST, LEG_NORTH_EAST);
					break;
				default:
					baseBedShape = EAST_SHAPE;
					legsBedShape = Shapes.or(LEG_NORTH_WEST, LEG_SOUTH_WEST);
					break;
			}
		}
		// After all of that is done, it then combines the two defined baseBedShape and legsBedShape together, effectively getting you your bounding box for your bed.
		return Shapes.or(baseBedShape, legsBedShape);
	}
}