package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearance;

public class SuperFrogAppearanceService {

    public static SuperFrogAppearance createAppearanceRequest(SuperFrogAppearance newAppearance) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAppearanceRequest'");
    }

    public SuperFrogAppearance requestTcuEventAppearance(SuperFrogAppearance appearance) {
        appearance.setIsTcuEvent(true);
        // Set other necessary fields or perform validations
        return appearanceRepository.save(appearance);
    }
  
    public SuperFrogAppearance approveRequest(String id) {
        SuperFrogAppearance appearance = appearanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Appearance not found"));
        appearance.setStatus("Approved");
        return appearanceRepository.save(appearance);
    }
    
    public SuperFrogAppearance rejectRequest(String id, String reason) {
        SuperFrogAppearance appearance = appearanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Appearance not found"));
        appearance.setStatus("Rejected");
        appearance.setRejectionReason(reason);
        return appearanceRepository.save(appearance);
    }
    
    

}
