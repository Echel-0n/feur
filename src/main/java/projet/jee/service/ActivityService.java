package projet.jee.service;

import projet.jee.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    public Activity save(Activity activity);

    public List<Activity> findAll();
    public Optional<Activity> findById(Long id);
    public List<Activity> findByNameLike(String name);

}
