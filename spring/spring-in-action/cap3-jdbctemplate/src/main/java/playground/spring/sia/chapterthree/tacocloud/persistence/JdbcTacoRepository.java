package playground.spring.sia.chapterthree.tacocloud.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import playground.spring.sia.chapterthree.tacocloud.model.Ingredient;
import playground.spring.sia.chapterthree.tacocloud.model.Taco;


import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    //
    // Reading data
    //

    //
    // Writing data
    //

    @Override
    public Taco save(Taco taco) {

        long tacoId = saveTacoInfo(taco);

        taco.setId(tacoId);

        for(Ingredient i: taco.getIngredients()) {

            saveIngredientToTaco(i, tacoId);

        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {

        taco.setCreatedAt(new Date());

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                "INSERT INTO TACO (name, createdAd) VALUES (?, ?)", Types.VARCHAR, Types.TIMESTAMP);

        PreparedStatementCreator c = factory.newPreparedStatementCreator(Arrays.asList(
                        taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(c, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient i, long tacoId) {

        jdbcTemplate.update(
                "INSERT INTO TACO_INGREDIENTS (taco, ingredient) VALUES (?, ?)", tacoId, i.getId());
    }
}
