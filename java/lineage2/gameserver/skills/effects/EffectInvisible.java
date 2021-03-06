/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package lineage2.gameserver.skills.effects;

import lineage2.gameserver.model.Effect;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.Summon;
import lineage2.gameserver.model.World;
import lineage2.gameserver.model.base.InvisibleType;
import lineage2.gameserver.stats.Env;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public final class EffectInvisible extends Effect
{
	/**
	 * Field _invisibleType.
	 */
	private InvisibleType _invisibleType = InvisibleType.NONE;
	
	/**
	 * Constructor for EffectInvisible.
	 * @param env Env
	 * @param template EffectTemplate
	 */
	public EffectInvisible(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	/**
	 * Method checkCondition.
	 * @return boolean
	 */
	@Override
	public boolean checkCondition()
	{
		if (!_effected.isPlayer())
		{
			return false;
		}
		Player player = (Player) _effected;
		if (player.isInvisible())
		{
			return false;
		}
		if (player.getActiveWeaponFlagAttachment() != null)
		{
			return false;
		}
		return super.checkCondition();
	}
	
	/**
	 * Method onStart.
	 */
	@Override
	public void onStart()
	{
		super.onStart();
		Player player = (Player) _effected;
		_invisibleType = player.getInvisibleType();
		player.setInvisibleType(InvisibleType.EFFECT);
		World.removeObjectFromPlayers(player);
	}
	
	/**
	 * Method onExit.
	 */
	@Override
	public void onExit()
	{
		super.onExit();
		Player player = (Player) _effected;
		if (!player.isInvisible())
		{
			return;
		}
		player.setInvisibleType(_invisibleType);
		player.broadcastUserInfo();
		for (Summon summon : player.getSummonList())
		{
			summon.broadcastCharInfo();
		}
	}
	
	/**
	 * Method onActionTime.
	 * @return boolean
	 */
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
