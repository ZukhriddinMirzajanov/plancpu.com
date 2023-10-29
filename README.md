# Plancpu Backen Code. 
#### Web Link to Frontend Code: https://github.com/ZukhriddinMirzajanov/plancpu.com_frontend

Plancpu is a software management platform, built in Java framework Spring Boot, React JS, PostgreSQL and deployed cloud

#### Web Link: https://plancpu.com

| Manager Credentials | Employee Credentials |
|-----------------------------------------|-----------------------------------------|
| Email: smith@apple.com |  Email: ali@gmail.com |
| Password- Qwer1234 | Password- Ali12345 |

|  Login                              |                                 |
|-----------------------------------------|-----------------------------------------|
| ![Image 1](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(192).png?raw=true) | ![Image 2](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(193).png?raw=true) |

|  Pages                              |                                 |
|-----------------------------------------|-----------------------------------------|
| ![Image 3](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(149).png?raw=true) | ![Image 4](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(150).png?raw=true) |
| ![Image 5](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(151).png?raw=true) | ![Image 6](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(152).png?raw=true) |
| ![Image 7](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(153).png?raw=true) | ![Image 8](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(154).png?raw=true) |
| ![Image 9](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(155).png?raw=true) | ![Image 10](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(158).png?raw=true) |
| ![Image 11](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(156).png?raw=true) | ![Image 12](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(157).png?raw=true) |
| ![Image 13](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(157).png?raw=true) | ![Image 14](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(159).png?raw=true) |
| ![Image 15](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(160).png?raw=true) | ![Image 16](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(161).png?raw=true) |
| ![Image 17](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(162).png?raw=true) | ![Image 18](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(162).png?raw=true) |
| ![Image 19](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(164).png?raw=true) | ![Image 20](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(164).png?raw=true) |
| ![Image 21](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(166).png?raw=true) | ![Image 22](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(167).png?raw=true) |
| ![Image 23](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(168).png?raw=true) | ![Image 24](https://github.com/ZukhriddinMirzajanov/plancpu_photos/blob/main/Screenshot%20(167).png?raw=true) |

## Features
- User Authentication:
  - Manger Login: Manager can access the system using manager email and password to manage employee data and tasks.
  - Employee Login: Employees can log in with their credentials to access Plancpu.
  - Admin Login: Only Plancpu admin can access to admin login credentials .

- Employee Management:
  - Manager Dashboard: The manager has the ability to add employees, creating projects, view analytics of project, view employees working hours that belongs to manager and view a list of all employees belongs to manager.
  - Employee Profile Update: Employees can update their profile information.
  - Admin Dashboard: The admin has the full control of Plancpu which deleting, deactivate users account and so on.

- Task Management:
  - Create Tasks: Manager and Employees can create tasks into specific project.
  - Delete and Edit Task: Manager can delete and edit eny tasks but simple employees can delete and edit only their tasks.
  - Filtering: Manger and Employees can filter tasks.

- Collaborative Working with Tasks:
  - Real Time Communication Between Client and Server: Plancpu enables real-time communication between the client and the server, allowing users to exchange information instantly. This feature ensures that updates, messages, and data are transmitted without delay, providing a seamless and interactive experience.
  - Open: Only Manager can open tasks which lays on backlog and then tasks will be appear on Active Sprint which in "Open" and then employees can assign and start the work.
  - In Progress: Before start working with specific task employees need assign and need change task status from "Open" to "In Progress".
  - In Review: When Employees finish the task they need to change the task status from "In Progress" to "In Review" and then someone can assign to review and if something not expected or problem did not solve reviewer can add comments to specific task by clicking comment icon on task.
  - Closed: If task approved by reviwer and then reviewer need to change the task status from "In Review" to "Closed" 

- Project Management:
  - Create Projects: Only Manager can create projects and can add employees into specific project.
  - Delete and Edit Projects: Only manager can delete and edit projects.
  - Filtering: Manger can filter projects

- Project Analytics:
  - Line and Bar Charts: Manager and Employees can see the analytics of specific project.
 
- Time Registration:
  - Report working hours: Employees can report their working hours.
  - View Employees Working Hours: Only Manager can see the Employees working report hours that's belongs to Manager 

## Getting Started
Setup:
- Backend:
  - Install Java 17 and any IDEA but I recomend for backend IntelliJ IDEA.
  - Install a PostgreSQL database and create database called plancpu.
  - Clone to the Repository: `git@github.com:ZukhriddinMirzajanov/plancpu.com.git`
  - Navigate into project and wait downloading and change application.proporties file with your database credentials and run

- Frontend:
  - Install v18.15.0 and any code editor or IDEA but I recomend for frontend VS Code.
  - Clone to the Repositroy: `git@github.com:ZukhriddinMirzajanov/plancpu.com_frontend.git`
  - Navigate into the project and run: npm install for installing node module packages and run: npn start to lunching web app but first you need run Backend.

## Technologies Used

- Front-End: JavaScript ReactJS, HTML, CSS, Bootstrap, Websocket
- Back-End: Java Spring Boot, Websocket, JWT
- Database: PostgreSQL
- Hosting Platform: AWS, Heroku, Firebase

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).z

## Contact

For any questions or inquiries, please reach out to the development team at [zukhriddin.mirzajanov@gmail.com]
