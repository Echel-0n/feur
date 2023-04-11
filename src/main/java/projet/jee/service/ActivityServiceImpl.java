package projet.jee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.jee.entity.Activity;
import projet.jee.repository.ActivityRepository;

import java.util.List;
import java.util.Optional;
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Override
    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> fetchActivityList(){
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> fetchActivityById(Long id) {
        return activityRepository.findById(id);
    }
}