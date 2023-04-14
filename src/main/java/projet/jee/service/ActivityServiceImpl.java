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
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> findAll(){
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }
    public List<Activity> findByNameLike(String name){
        return activityRepository.findByNameLike(name);
    }

    public void update(Activity a) {
        activityRepository.updateNameAndTelAndAddressAndVilleAndDescriptionByActivityId(
                a.getName(),
                a.getTel(),
                a.getAddress(),
                a.getVille(),
                a.getDescription(),
                a.getActivityId()
        );
    }
    public void delete(Long id) {
        activityRepository.deleteById(id);
    }
}
