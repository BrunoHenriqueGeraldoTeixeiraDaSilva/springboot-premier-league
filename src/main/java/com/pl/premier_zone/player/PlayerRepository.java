package com.pl.premier_zone.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //Player specifies the entity type and String specifies the primary key type
public interface PlayerRepository extends JpaRepository<Player, String> {
    /*
    Only findByName and deleteByName were added to the repository because they were the only methods that directly needed to interact with the database.
    The rest of the filtering logic was simple enough to be handled in the service layer using Java streams.
     */
    void deleteByName(String playerName);
    //The use of optional is to handle cases where the player might not be found
    //in the repository
    Optional<Player> findByName (String name);

    /*
    BTW This is called "Query Method Naming", and it saves a lot of boilerplate code.
    in your PlayerRepository interface, Spring Data JPA automatically creates the SQL query for you behind the scenes.
    You don’t have to manually write SQL — Spring sees findByName and understands it means:
    "Search the player table where the name column matches the given name."
     */

}


