package com.hb.tricount.business.impl;

import com.hb.tricount.business.PaymentService;
import com.hb.tricount.dto.CreatePaymentDTO;
import com.hb.tricount.dto.PaymentDTO;
import com.hb.tricount.entity.Group;
import com.hb.tricount.entity.Payment;
import com.hb.tricount.entity.Person;
import com.hb.tricount.repository.GroupRepository;
import com.hb.tricount.repository.PaymentRepository;
import com.hb.tricount.repository.PersonRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

        private final PaymentRepository paymentRepository;
        private final PersonRepository personRepository;
        private final GroupRepository groupRepository;

        public PaymentServiceImpl(PaymentRepository paymentRepository, PersonRepository personRepository,
                        GroupRepository groupRepository) {
                this.paymentRepository = paymentRepository;
                this.personRepository = personRepository;
                this.groupRepository = groupRepository;
        }

        @Override
        @Transactional
        public PaymentDTO recordPayment(CreatePaymentDTO dto) {
                Person debtor = personRepository.findById(dto.getDebtorId())
                                .orElseThrow(() -> new IllegalArgumentException("Débiteur introuvable"));
                Person creditor = personRepository.findById(dto.getCreditorId())
                                .orElseThrow(() -> new IllegalArgumentException("Créancier introuvable"));
                Group group = groupRepository.findById(dto.getGroupId())
                                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable"));

                Payment payment = Payment.builder()
                                .amount(dto.getAmount())
                                .date(dto.getDate())
                                .debtor(debtor)
                                .creditor(creditor)
                                .group(group)
                                .build();

                Payment saved = paymentRepository.save(payment);

                return PaymentDTO.builder()
                                .id(saved.getId())
                                .amount(saved.getAmount())
                                .date(saved.getDate())
                                .fromId(debtor.getId())
                                .toId(creditor.getId())
                                .groupId(group.getId())
                                .build();
        }

        @Override
        public List<PaymentDTO> getPaymentsByGroup(Long groupId) {
                return paymentRepository.findByGroupId(groupId)
                                .stream()
                                .map(p -> PaymentDTO.builder()
                                                .id(p.getId())
                                                .amount(p.getAmount())
                                                .date(p.getDate())
                                                .fromId(p.getDebtor().getId())
                                                .toId(p.getCreditor().getId())
                                                .groupId(groupId)
                                                .build())
                                .toList();
        }

}
