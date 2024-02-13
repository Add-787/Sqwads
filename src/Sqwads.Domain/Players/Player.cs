using System.ComponentModel.DataAnnotations;
using Sqwads.Domain.Common;

namespace Sqwads.Domain.Players;

public class Player : Entity
{
    [Required]
    public string Name { get; private set; }

    [Required]
    public DateTime DateOfBirth { get; private set; }

    [Required]
    public string Country { get; private set; } = "N/A";

    [Required]
    public string Club { get; private set; } = "N/A";

    [Required]
    public Position Position { get; private set; }

    public Player(string name)
    {
        if (name is null || name == "")
            throw new ArgumentException("Name cannot be null or empty.");
        Name = name;
    }

    public int GetAge()
    {
        DateTime currDate = DateTime.Now;
        int age = currDate.Year - DateOfBirth.Year;

        if(currDate.Month < DateOfBirth.Month || (currDate.Month == DateOfBirth.Month) && currDate.Day < DateOfBirth.Day)
        {
            age--;
        }

        return age;
    }
}
