package playground.spring.sia.chapterthree.tacocloud.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.spring.sia.chapterthree.tacocloud.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    //
    // Reading data
    //

    @Override
    public Ingredient findOne(String id) {

        return jdbcTemplate.queryForObject(
                "SELECT id, name, type from INGREDIENT WHERE id=?",
                this::mapRowToIngredient, id);
    }

    @Override
    public Iterable<Ingredient> findAll() {

        return jdbcTemplate.query(
                "SELECT id, name, type from INGREDIENT",
                this::mapRowToIngredient);
    }

    //
    // Writing data
    //

    @Override
    public Ingredient save(Ingredient ingredient) {

        jdbcTemplate.update(
                "INSERT INTO INGREDIENT (id, name, type) VALUES (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNumber) throws SQLException {

        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
