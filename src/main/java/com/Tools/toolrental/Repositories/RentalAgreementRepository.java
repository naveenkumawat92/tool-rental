package com.Tools.toolrental.Repositories;

import com.Tools.toolrental.Model.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreement,Integer> {
}
