package projet.jee.service;

import projet.jee.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    Activity save(Activity activity);

    List<Activity> findAll();
     Optional<Activity> findById(Long id);
    List<Activity> findByNameLike(String name);
    void update(Activity activity);
    void delete(Long id);

}
