package projet.jee.service;

import projet.jee.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    public Activity saveActivity(Activity activity);

    public List<Activity> fetchActivityList();
    public Optional<Activity> fetchActivityById(Long id);
}
