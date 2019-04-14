package shared.network.init;

import camera.Focused;
import com.artemis.Component;
import com.artemis.FluidIteratingSystem;
import entity.character.attributes.*;
import entity.character.equipment.Armor;
import entity.character.equipment.Helmet;
import entity.character.equipment.Shield;
import entity.character.equipment.Weapon;
import entity.character.info.*;
import entity.character.parts.Body;
import entity.character.parts.Head;
import entity.character.states.*;
import entity.character.status.*;
import entity.world.*;
import entity.world.Object;
import graphics.FX;
import map.Cave;
import map.Map;
import movement.Destination;
import movement.Moving;
import movement.RandomMovement;
import net.mostlyoriginal.api.network.marshal.common.MarshalDictionary;
import network.Network;
import physics.AOPhysics;
import physics.Attack;
import physics.AttackAnimation;
import position.Pos2D;
import position.WorldPos;
import shared.interfaces.CharClass;
import shared.interfaces.Constants;
import shared.interfaces.Hero;
import shared.map.AutoTiler;
import shared.map.model.MapDescriptor;
import shared.map.model.TILE_BITS;
import shared.map.model.TerrainType;
import shared.map.model.TilesetConfig;
import shared.model.AttackType;
import shared.model.Spell;
import shared.model.loaders.ObjectLoader;
import shared.model.loaders.SpellLoader;
import shared.model.lobby.Lobby;
import shared.model.lobby.Player;
import shared.model.lobby.Room;
import shared.model.lobby.Team;
import shared.model.readers.DescriptorsReader;
import shared.model.readers.Loader;
import shared.model.readers.Reader;
import shared.network.combat.AttackRequest;
import shared.network.combat.AttackResponse;
import shared.network.combat.SpellCastRequest;
import shared.network.interaction.DropItem;
import shared.network.interaction.MeditateRequest;
import shared.network.interaction.TakeItemRequest;
import shared.network.interaction.TalkRequest;
import shared.network.interfaces.DefaultNotificationProcessor;
import shared.network.interfaces.INotificationProcessor;
import shared.network.interfaces.IResponseProcessor;
import shared.network.inventory.InventoryUpdate;
import shared.network.inventory.ItemActionRequest;
import shared.network.lobby.*;
import shared.network.lobby.player.ChangeHeroNotification;
import shared.network.lobby.player.ChangeTeamNotification;
import shared.network.lobby.player.PlayerLoginRequest;
import shared.network.lobby.player.ReadyNotification;
import shared.network.movement.MovementNotification;
import shared.network.movement.MovementRequest;
import shared.network.movement.MovementResponse;
import shared.network.notifications.EntityUpdate;
import shared.network.notifications.FXNotification;
import shared.network.notifications.RemoveEntity;
import shared.objects.factory.ObjectFactory;
import shared.objects.types.*;
import shared.objects.types.common.*;
import shared.util.MapUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;


public class NetworkDictionary extends MarshalDictionary {

    public NetworkDictionary() {
        registerAll(
                // Game Requests
                MovementRequest.class,
                AttackRequest.class,
                MeditateRequest.class,
                TalkRequest.class,
                ItemActionRequest.class,
                TakeItemRequest.class,
                SpellCastRequest.class,

                // Game Responses
                MovementResponse.class,
                AttackResponse.class,

                // Game Notifications
                EntityUpdate.class,
                RemoveEntity.class,
                MovementNotification.class,
                InventoryUpdate.class,
                IResponseProcessor.class,
                INotificationProcessor.class,
                DefaultNotificationProcessor.class,
                DropItem.class,
                FXNotification.class,

                // Lobby
                Lobby.class,
                Player.class,
                Room.class,
                Room[].class,
                Team.class,

                // Lobby Player
                ChangeHeroNotification.class,
                ChangeTeamNotification.class,
                ReadyNotification.class,

                // Lobby Requests
                CreateRoomRequest.class,
                ExitRoomRequest.class,
                JoinLobbyRequest.class,
                JoinRoomRequest.class,
                StartGameRequest.class,
                PlayerLoginRequest.class,

                // Lobby Responses
                CreateRoomResponse.class,
                JoinLobbyResponse.class,
                JoinRoomResponse.class,
                StartGameResponse.class,

                // Lobby Notificaitons
                JoinRoomNotification.class,
                NewRoomNotification.class,

                // Other
                boolean[][].class,
                boolean[].class,
                int[][].class,
                int[].class,
                Integer[].class,
                HashMap.class,
                HashSet.class,
                MapUtils.class,
                ConcurrentLinkedDeque.class,
                Component.class,
                Component[].class,
                Class.class,
                Class[].class,
                Spell.class,
                CharClass.class,
                CharHero.class,
                Hero.class,
                Loader.class,
                Reader.class,

                FluidIteratingSystem.class,
                ObjectLoader.class,
                SpellLoader.class,
                SpellLoader.SpellSetter.class,
                DescriptorsReader.class,
                AutoTiler.class,
                MapDescriptor.class,
                MapDescriptor.MapDescriptorBuilder.class,
                TilesetConfig.class,
                TerrainType.class,
                TILE_BITS.class,
                Type.class,
                ObjectFactory.class,
                ArmorObj.class,
                ArrowObj.class,
                BoatObj.class,
                DepositObj.class,
                DoorObj.class,
                DrinkObj.class,
                Food.class,
                HelmetObj.class,
                IEquipable.class,
                IFillObject.class,
                KeyObj.class,
                MagicObj.class,
                MineralObj.class,
                MusicalObj.class,
                ObjWithClasses.class,
                PosterObj.class,
                PotionKind.class,
                PotionObj.class,
                ShieldObj.class,
                SpellObj.class,
                WeaponObj.class,
                AnvilObj.class,
                BonfireObj.class,
                BookObj.class,
                ContainerObj.class,
                FlowerObj.class,
                ForgeObj.class,
                ForumObj.class,
                FurnitureObj.class,
                GemObj.class,
                GoldObj.class,
                JewelObj.class,
                StainObj.class,
                TeleportObj.class,
                TreeObj.class,
                WoodObj.class,

                // Components
                SpellBook.class,
                Map.class,
                Cave.class,
                Obj.class,
                Inventory.class,
                Inventory.Item.class,
                Inventory.Item[].class,
                Optional.class,
                Pos2D.class,
                AOPhysics.class,
                AOPhysics.Movement.class,
                CombatMessage.class,
                CombatMessage.Kind.class,
                AttackType.class,
                Constants.Heading.class,
                Name.class,
                WorldPos.class,
                Focused.class,
                Clan.class,
                Description.class,
                Immobile.class,
                Meditating.class,
                Navigating.class,
                Resting.class,
                Writing.class,
                Object.class,
                Ground.class,
                PotionKind.class,
                Criminal.class,
                Exp.class,
                GM.class,
                Health.class,
                Hungry.class,
                Level.class,
                Mana.class,
                Stamina.class,
                Thirst.class,
                CanWrite.class,
                entity.character.Character.class,
                Body.class,
                Dialog.class,
                Dialog.Kind.class,
                Head.class,
                Heading.class,
                Helmet.class,
                Shield.class,
                Weapon.class,
                FX.class,
                Destination.class,
                Moving.class,
                RandomMovement.class,
                Network.class,
                Attack.class,
                Agility.class,
                Strength.class,
                Intelligence.class,
                Charisma.class,
                Constitution.class,
                Hit.class,
                Armor.class,
                Footprint.class,
                AttackAnimation.class
        );
    }

    private void registerAll(Class... classes) {
        topId = 40;
        for (Class clazz : classes) {
            register(topId++, clazz);
        }
    }
}
